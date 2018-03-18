package br.hackaton.skipthedishes;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import br.hackathon.skipthedishes.controller.UsersController;
import br.hackathon.skipthedishes.model.Product;
import br.hackathon.skipthedishes.model.ProductOrder;
import br.hackathon.skipthedishes.model.User;
import br.hackathon.skipthedishes.utils.Encryption;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsersController.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUser {

	public static final String USER_URL = "http://localhost:8080/user/";
	public static final String PRODUCT_URL = "http://localhost:8080/product/";
	public static final String ORDER_URL = "http://localhost:8080/order/";

	
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void test11_cleanDatabaseAndCreateUser() {
		//Given an user
		User userInsert = new User("Sirius", "sirius", Encryption.getSHA1("test"));
		
		//When you clean the database and try to put (insert a user) and then get
		restTemplate.delete(USER_URL, User.class);
		restTemplate.put(USER_URL, userInsert);
		User user = restTemplate.getForObject(USER_URL + "1", User.class); 
	
		//Then we should have a list that is not empty.
		Assert.assertEquals("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", user.getPassword());
	}
	
	@Test
	public void test12_getUser() {
		//Given an user id
		Integer id = 1;
		
		//When we get the user
		User user = restTemplate.getForObject(USER_URL + id, User.class);
		
		//Then the user's name should be Sirius
		Assert.assertEquals("Sirius", user.getName());
	}

	
	@Test
	public void test13_updateUser() {
		//Given an user id
		Integer id = 1;
		
		//When we get the user, change it's login and post it, and get it again
		User user = restTemplate.getForObject(USER_URL + id, User.class);
		user.setLogin("newlogin");
		restTemplate.postForObject(USER_URL + id, user, User.class);
		user = null;
		user = restTemplate.getForObject(USER_URL + id, User.class);
		
		
		//Then the user's name should be Sirius
		Assert.assertEquals("newlogin", user.getLogin());
	}
	


	@Test
	public void test21_cleanDatabaseAndCreateProduct() {
		//Given an product
		Product productInsert = new Product("Pizza", 12.9d, 10);
		
		//When you clean the database and try to put (insert a product) and then get
		restTemplate.delete(PRODUCT_URL, Product.class);
		restTemplate.put(PRODUCT_URL, productInsert);
		Product product = restTemplate.getForObject(PRODUCT_URL + "1", Product.class); 
	
		//Then we should have a list that is not empty.
		Assert.assertEquals(new Double(12.9), product.getPrice());
	}
	
	@Test
	public void test22_getProduct() {
		//Given an product id
		Integer id = 1;
		
		//When we get the product
		Product product = restTemplate.getForObject(PRODUCT_URL + id, Product.class);
		
		//Then the product's name should be Pizza
		Assert.assertEquals("Pizza", product.getName());
	}

	
	@Test
	public void test23_updateProduct() {
		//Given an product id
		Integer id = 1;
		
		//When we get the product, change it's price and post it, and get it again
		Product product = restTemplate.getForObject(PRODUCT_URL + id, Product.class);
		product.setPrice(new Double(14.1));
		restTemplate.postForObject(PRODUCT_URL + id, product, Product.class);
		product = null;
		product = restTemplate.getForObject(PRODUCT_URL + id, Product.class);
		
		
		//Then the product's name should be Sirius
		Assert.assertEquals(new Double(14.1), product.getPrice());
	}

	@Test
	public void test31_cleanDatabaseAndCreateOrder() {
		//Given quantity and product id
		Integer quantity = 5;
		Integer id_product = 1;
		//Given an order id
		Integer id = 1;
		
		//Given an order
		ProductOrder order = new ProductOrder(id_product,quantity,1);
		List<ProductOrder> listOrder = new ArrayList<ProductOrder>();
		listOrder.add(order);
		
		//When you clean the database and try to put (insert a order) and then get it's status and also the product
		restTemplate.delete(ORDER_URL, ProductOrder.class);
		restTemplate.put(ORDER_URL, listOrder);
		
		String status = restTemplate.getForObject(ORDER_URL + id, String.class);
		Product product = restTemplate.getForObject(PRODUCT_URL + id_product, Product.class);
		
		//Then we should have a list that is not empty.
		Assert.assertEquals("Processed", status);
		//And the product availability should decrease in 5.
		Assert.assertEquals(new Integer(5), product.getAvailability());
		
		
	}
	
	@Test
	public void test32_cancelOrder() {
		//Given quantity and product id
		Integer quantity = 10; // expected quantity of product id 1 after canceling the order
		Integer id_product = 1;
		//Given an order id
		Integer id = 1;
		
		//When you cancel that order and get it's status
		restTemplate.delete(ORDER_URL + id, ProductOrder.class);
		String status = restTemplate.getForObject(ORDER_URL + id, String.class);
		Product product = restTemplate.getForObject(PRODUCT_URL + id_product, Product.class);
		
		//Then we should have a status cancelled and a quantity of 10
		Assert.assertEquals("Cancelled", status);
		Assert.assertEquals(new Integer(quantity), product.getAvailability());
	}

	
	@Test
	public void test41_deleteUser() {
		//Given an user id
		Integer id = 1;
		
		//When we delete this user and try to get it
		restTemplate.delete(USER_URL + id, User.class);
		User user = restTemplate.getForObject(USER_URL + id, User.class);
		
		//Then the user's name should be null
		Assert.assertNull(user.getName());
	}	
	
	@Test
	public void test42_deleteProduct() {
		//Given an product id
		Integer id = 1;
		
		//When we delete this product and try to get it
		restTemplate.delete(PRODUCT_URL + id, Product.class);
		Product product = restTemplate.getForObject(PRODUCT_URL + id, Product.class);
		
		//Then the product's name should be null
		Assert.assertNull(product.getName());
	}
	
	
}
