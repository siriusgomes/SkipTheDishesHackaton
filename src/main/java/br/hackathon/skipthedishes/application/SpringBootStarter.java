package br.hackathon.skipthedishes.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.hackathon.skipthedishes.controller.UsersController;

@SpringBootApplication
public class SpringBootStarter {

	  public static void main(String[] args) throws Exception {
	        SpringApplication.run(UsersController.class, args);
	    }
}
