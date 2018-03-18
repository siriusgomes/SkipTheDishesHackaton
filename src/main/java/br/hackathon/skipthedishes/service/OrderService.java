package br.hackathon.skipthedishes.service;


import java.util.List;

import br.hackathon.skipthedishes.model.ProductOrder;

public interface OrderService {
	
	Boolean createOrder(List<ProductOrder> listProduct);
	
	String getOrderStatus(long id_order);
	
	Boolean cancelOrder(long id_order);
	
	void deleteAllOrders();
	
}
