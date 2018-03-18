package br.hackathon.skipthedishes.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.hackathon.skipthedishes.model.Product;
import br.hackathon.skipthedishes.model.ProductOrder;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	static Connection conn;

	@Autowired
	ProductService productService;

	private Boolean result = true;

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	static {

		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Boolean createOrder(List<ProductOrder> listProduct) {
		this.result = true;
		listProduct.forEach(p -> {
			Product product = productService.findById(p.getId_product());
			if (!this.getResult() || p.getQuantity() > product.getAvailability()) {
				// Can't continue with order.
				this.setResult(false);
			}
		});

		// If the order can continue, let's update the availability of the
		// products and insert the orders with a status true
		if (this.getResult()) {
			listProduct.forEach(p -> {
				Product product = productService.findById(p.getId_product());
				product.setAvailability(product.getAvailability() - p.getQuantity());
				productService.updateProduct(product);

				try {
					PreparedStatement st = conn.prepareStatement(
							"insert into orders(id_user, status, id_product, quantity) values(?,?,?, ?)");
					st.setLong(1, p.getId_user());
					st.setString(2, "Processed");
					st.setLong(3, product.getId_product());
					st.setInt(4, p.getQuantity());
					st.execute();
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		return this.getResult();
	}

	@Override
	public String getOrderStatus(long id_order) {
		String status = null;
		try {
			PreparedStatement st = conn.prepareStatement("select status from orders where id_order = ?");
			st.setLong(1, id_order);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				status = rs.getString("status");
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Boolean cancelOrder(long id_order) {
		Boolean cancelled = false;
		try {
			PreparedStatement st = conn
					.prepareStatement("select * from orders where id_order = ? and status != 'Cancelled'");
			st.setLong(1, id_order);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				Product product = productService.findById(rs.getInt("id_product"));
				product.setAvailability(product.getAvailability() + rs.getInt("quantity"));
				productService.updateProduct(product);
				st = conn.prepareStatement("update orders set status = 'Cancelled' where id_order = ?");
				st.setLong(1, id_order);
				st.execute();
				cancelled = true;
				st.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cancelled;
	}

	@Override
	public void deleteAllOrders() {
		try {
			PreparedStatement st = conn.prepareStatement("delete from orders");
			st.execute();
			st = conn.prepareStatement("ALTER TABLE orders ALTER COLUMN id_order RESTART WITH 1");
			st.execute();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
