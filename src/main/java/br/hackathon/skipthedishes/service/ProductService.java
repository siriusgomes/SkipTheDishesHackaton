package br.hackathon.skipthedishes.service;


import java.util.List;

import br.hackathon.skipthedishes.model.Product;

public interface ProductService {
	
	Product findById(long id);
	
	Product findByName(String name);
	
	void saveProduct(Product product);
	
	void updateProduct(Product product);
	
	void deleteProductById(long id);

	List<Product> findAllProducts();
	
	boolean isProductExist(Product product);
	
	void deleteAllProducts();
}
