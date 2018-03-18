package br.hackathon.skipthedishes.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.hackathon.skipthedishes.model.Product;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	static Connection conn;

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

	public List<Product> findAllProducts() {
		List<Product> listProducts = new ArrayList<Product>();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from products");
			while (rs.next()) {

				int id_product = rs.getInt("id_product");
				String name = rs.getString("name");
				Double price = rs.getDouble("price");
				Integer availability = rs.getInt("availability");
				listProducts.add(new Product(id_product, name, price, availability));

			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listProducts;
	}

	public Product findById(long id_product_query) {
		Product product = null;
		try {
			PreparedStatement st = conn.prepareStatement("select * from products where id_product = ?");
			st.setLong(1, id_product_query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {

				int id_product = rs.getInt("id_product");
				String name = rs.getString("name");
				Double price = rs.getDouble("price");
				Integer availability = rs.getInt("availability");
				product = new Product(id_product, name, price, availability);

			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}

	

	public Product findByName(String name_query) {
		Product product = null;
		try {
			PreparedStatement st = conn.prepareStatement("select * from products where name = ?");
			st.setString(1, name_query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {

				int id_product = rs.getInt("id_product");
				String name = rs.getString("name");
				Double price = rs.getDouble("price");
				Integer availability = rs.getInt("availability");
				product = new Product(id_product, name, price, availability);

			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}
	
	public void saveProduct(Product product) { 
		try {
			PreparedStatement st = conn.prepareStatement("insert into products (name, price, availability) values  (?,?,?)");
			st.setString(1, product.getName());
			st.setDouble(2, product.getPrice());
			st.setInt(3, product.getAvailability());
			st.execute();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateProduct(Product product) {
		try {
			PreparedStatement st = conn.prepareStatement("update products set name = ?, price = ?, availability = ? where id_product = ?");
			st.setString(1, product.getName());
			st.setDouble(2, product.getPrice());
			st.setInt(3, product.getAvailability());
			st.setLong(4, product.getId_product());
			st.execute();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteProductById(long id) {
		try {
			PreparedStatement st = conn.prepareStatement("delete from products where id_product = ?");
			st.setLong(1, id);
			st.execute();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isProductExist(Product product) {
		return findByName(product.getName()) != null;
	}

	@Override
	public void deleteAllProducts() {
		try {
			PreparedStatement st = conn.prepareStatement("delete from products");
			st.execute();
			st = conn.prepareStatement("ALTER TABLE products ALTER COLUMN id_product RESTART WITH 1");
			st.execute();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
