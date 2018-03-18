package br.hackathon.skipthedishes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

	private long id_product;
	
	private String name;
	
	private Double price;
	
	private Integer availability;

	public Product(long id_product, String name, Double price, Integer availability) {
		super();
		this.id_product = id_product;
		this.name = name;
		this.price = price;
		this.availability = availability;
	}
	
	public Product(String name, Double price, Integer availability) {
		super();
		this.name = name;
		this.price = price;
		this.availability = availability;
	}
	
	public Product() {
		
	}

	public long getId_product() {
		return id_product;
	}

	public void setId_product(long id_product) {
		this.id_product = id_product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getAvailability() {
		return availability;
	}

	public void setAvailability(Integer availability) {
		this.availability = availability;
	}

	@Override
	public String toString() {
		return "Product [id_product=" + id_product + ", name=" + name + ", price=" + price + ", availability="
				+ availability + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((availability == null) ? 0 : availability.hashCode());
		result = prime * result + (int) (id_product ^ (id_product >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (availability == null) {
			if (other.availability != null)
				return false;
		} else if (!availability.equals(other.availability))
			return false;
		if (id_product != other.id_product)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	

}
