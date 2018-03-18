package br.hackathon.skipthedishes.service;


import java.util.List;

import br.hackathon.skipthedishes.model.User;

public interface UserService {
	
	User findById(long id);
	
	User findByName(String name);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserById(long id);

	List<User> findAllUsers();
	
	boolean isUserExist(User user);
	
	void deleteAllUsers();
}
