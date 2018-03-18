package br.hackathon.skipthedishes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import br.hackathon.skipthedishes.model.Product;
import br.hackathon.skipthedishes.service.ProductService;
import br.hackathon.skipthedishes.utils.CustomReturn;

@Controller
//@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages={"br.hackathon.skipthedishes"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
public class ProductsController {
	
	public static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
	
	
	@Autowired
	ProductService service;
	
    
 // -------------------Retrieve All Products---------------------------------------------

 	@RequestMapping(value = "/product/", method = RequestMethod.GET)
 	public ResponseEntity<List<Product>> listAllProducts() {
 		List<Product> products = service.findAllProducts();
 		if (products.isEmpty()) {
 			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
 			// You many decide to return HttpStatus.NOT_FOUND
 		}
 		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
 	}

 	// -------------------Retrieve Single Product------------------------------------------

 	
	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
 	public ResponseEntity<?> getProduct(@PathVariable("id") long id) {
 		logger.info("Fetching Product with id {}", id);
 		Product product = service.findById(id);
 		if (product == null) {
 			logger.error("Product with id {} not found.", id);
 			return new ResponseEntity<Object>(new CustomReturn("Product with id " + id 
 					+ " not found"), HttpStatus.NOT_FOUND);
 		}
 		return new ResponseEntity<Product>(product, HttpStatus.OK);
 	}

 	// -------------------Create a Product-------------------------------------------

 	@RequestMapping(value = "/product/", method = RequestMethod.PUT)
 	public ResponseEntity<?> createProduct(@RequestBody Product product, UriComponentsBuilder ucBuilder) {
 		logger.info("Creating Product : {}", product);

 		if (service.isProductExist(product)) {
 			logger.error("Unable to create. A Product with name {} already exist", product.getName());
 			return new ResponseEntity<Object>(new CustomReturn("Unable to create. A Product with name " + 
 			product.getName() + " already exist."),HttpStatus.CONFLICT);
 		}
 		service.saveProduct(product);

 		HttpHeaders headers = new HttpHeaders();
 		headers.setLocation(ucBuilder.path("/api/product/{id}").buildAndExpand(product.getId_product()).toUri());
 		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
 	}

 	// ------------------- Update a Product ------------------------------------------------

 	@RequestMapping(value = "/product/{id}", method = RequestMethod.POST)
 	public ResponseEntity<?> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
 		logger.info("Updating Product with id {}", id);

 		Product currentProduct = service.findById(id);

 		if (currentProduct == null) {
 			logger.error("Unable to update. Product with id {} not found.", id);
 			return new ResponseEntity<Object>(new CustomReturn("Unable to upate. Product with id " + id + " not found."),
 					HttpStatus.NOT_FOUND);
 		}

 		currentProduct.setName(product.getName());
 		currentProduct.setPrice(product.getPrice());
 		currentProduct.setAvailability(product.getAvailability());

 		service.updateProduct(currentProduct);
 		return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
 	}

 	// ------------------- Delete a Product-----------------------------------------

 	@RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
 	public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {
 		logger.info("Fetching & Deleting Product with id {}", id);

 		Product product = service.findById(id);
 		if (product == null) {
 			logger.error("Unable to delete. Product with id {} not found.", id);
 			return new ResponseEntity<Object>(new CustomReturn("Unable to delete. Product with id " + id + " not found."),
 					HttpStatus.NOT_FOUND);
 		}
 		service.deleteProductById(id);
 		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
 	}
 	
 	
 	// ------------------- Delete all Products-----------------------------------------
 	
 	@RequestMapping(value = "/product/", method = RequestMethod.DELETE)
	public ResponseEntity<Product> deleteAllProducts() {
		logger.info("Deleting All Products");

		service.deleteAllProducts();
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}

    
}