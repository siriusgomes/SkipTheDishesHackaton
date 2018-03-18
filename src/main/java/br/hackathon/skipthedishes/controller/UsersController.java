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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import br.hackathon.skipthedishes.model.User;
import br.hackathon.skipthedishes.service.UserService;
import br.hackathon.skipthedishes.utils.CustomReturn;

@Controller
//@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages={"br.hackathon.skipthedishes"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
public class UsersController {
	
	public static final Logger logger = LoggerFactory.getLogger(UsersController.class);
	
	
	@Autowired
	UserService service;
	

	@RequestMapping(value = "/helloworld", method = RequestMethod.GET)
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping(value = "/")
    public String mainPage(){
      return "redirect:/helloworld";
    }

    
    
 // -------------------Retrieve All Users---------------------------------------------

 	@RequestMapping(value = "/user/", method = RequestMethod.GET)
 	public ResponseEntity<List<User>> listAllUsers() {
 		List<User> users = service.findAllUsers();
 		if (users.isEmpty()) {
 			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
 			// You many decide to return HttpStatus.NOT_FOUND
 		}
 		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
 	}

 	// -------------------Retrieve Single User------------------------------------------

 	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
 	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
 		logger.info("Fetching User with id {}", id);
 		User user = service.findById(id);
 		if (user == null) {
 			logger.error("User with id {} not found.", id);
 			return new ResponseEntity<Object>(new CustomReturn("User with id " + id 
 					+ " not found"), HttpStatus.NOT_FOUND);
 		}
 		return new ResponseEntity<User>(user, HttpStatus.OK);
 	}

 	// -------------------Create a User-------------------------------------------

 	@RequestMapping(value = "/user/", method = RequestMethod.PUT)
 	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
 		logger.info("Creating User : {}", user);

 		if (service.isUserExist(user)) {
 			logger.error("Unable to create. A User with name {} already exist", user.getName());
 			return new ResponseEntity<Object>(new CustomReturn("Unable to create. A User with name " + 
 			user.getName() + " already exist."),HttpStatus.CONFLICT);
 		}
 		service.saveUser(user);

 		HttpHeaders headers = new HttpHeaders();
 		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId_user()).toUri());
 		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
 	}

 	// ------------------- Update a User ------------------------------------------------

 	@RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
 	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
 		logger.info("Updating User with id {}", id);

 		User currentUser = service.findById(id);

 		if (currentUser == null) {
 			logger.error("Unable to update. User with id {} not found.", id);
 			return new ResponseEntity<Object>(new CustomReturn("Unable to upate. User with id " + id + " not found."),
 					HttpStatus.NOT_FOUND);
 		}

 		currentUser.setName(user.getName());
 		currentUser.setLogin(user.getLogin());
 		currentUser.setPassword(user.getPassword());

 		service.updateUser(currentUser);
 		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
 	}

 	// ------------------- Delete a User-----------------------------------------

 	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
 	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
 		logger.info("Fetching & Deleting User with id {}", id);

 		User user = service.findById(id);
 		if (user == null) {
 			logger.error("Unable to delete. User with id {} not found.", id);
 			return new ResponseEntity<Object>(new CustomReturn("Unable to delete. User with id " + id + " not found."),
 					HttpStatus.NOT_FOUND);
 		}
 		service.deleteUserById(id);
 		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
 	}
 	
 	
 	// ------------------- Delete all Users-----------------------------------------
 	
 	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {
		logger.info("Deleting All Users");

		service.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

    
}