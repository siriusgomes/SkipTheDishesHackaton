package br.hackathon.skipthedishes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import br.hackathon.skipthedishes.model.Order;
import br.hackathon.skipthedishes.model.ProductOrder;
import br.hackathon.skipthedishes.service.OrderService;
import br.hackathon.skipthedishes.utils.CustomReturn;

@Controller
//@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages={"br.hackathon.skipthedishes"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
public class OrdersController {
	
	public static final Logger logger = LoggerFactory.getLogger(OrdersController.class);
	
	
	@Autowired
	OrderService service;
	
    
 // -------------------Retrieve All Orders---------------------------------------------
//
// 	@RequestMapping(value = "/order/", method = RequestMethod.GET)
// 	public ResponseEntity<List<Order>> listAllOrders() {
// 		List<Order> orders = service.findAllOrders();
// 		if (orders.isEmpty()) {
// 			return new ResponseEntity(HttpStatus.NO_CONTENT);
// 			// You many decide to return HttpStatus.NOT_FOUND
// 		}
// 		return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);
// 	}

 	// -------------------Retrieve An Order Status------------------------------------------

 	
	@RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
 	public ResponseEntity<?> getOrderStatus(@PathVariable("id") long id) {
 		logger.info("Fetching Order with id {}", id);
 		String status = service.getOrderStatus(id);
 		if (status == null) {
 			logger.error("Order with id {} not found.", id);
 			return new ResponseEntity<Object>(new CustomReturn("Order with id " + id 
 					+ " not found"), HttpStatus.NOT_FOUND);
 		}
 		return new ResponseEntity<String>(status, HttpStatus.OK);
 	}

 	// -------------------Create a Order-------------------------------------------

 	@RequestMapping(value = "/order/", method = RequestMethod.PUT)
 	public ResponseEntity<?> createOrder(@RequestBody List<ProductOrder> productOrder , UriComponentsBuilder ucBuilder) {
 		logger.info("Creating Order : {}", productOrder);

 	
 		Boolean created = service.createOrder(productOrder);

 		//HttpHeaders headers = new HttpHeaders();
 		//headers.setLocation(ucBuilder.path("/api/order/{id}").buildAndExpand(order.getId_order()).toUri());
 		return new ResponseEntity<Object>((created ? new CustomReturn("Created") : new CustomReturn("Not created! Check availability of products")) ,HttpStatus.CREATED);
 	}

 	// ------------------- Cancel a Order ------------------------------------------------

 	@RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
 	public ResponseEntity<?> updateOrder(@PathVariable("id") long id) {
 		logger.info("Cancelling Order with id {}", id);

 		Boolean cancelled = service.cancelOrder(id);
 		if (!cancelled) {
 			logger.error("Order with id {} could not be cancelled.", id);
 			return new ResponseEntity<Object>(new CustomReturn("Order with id " + id 
 					+ " could not be cancelled."), HttpStatus.NOT_FOUND);
 		}

 		return new ResponseEntity<Object>((cancelled ? new CustomReturn("Cancelled") : new CustomReturn("Not cancelled! Check order id")),HttpStatus.OK);
 	}

 	
 	// ------------------- Delete all Orders-----------------------------------------
 	
 	@RequestMapping(value = "/order/", method = RequestMethod.DELETE)
	public ResponseEntity<Order> deleteAllOrders() {
		logger.info("Deleting All Orders");

		service.deleteAllOrders();
		return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
	}

    
}