package br.hackathon.skipthedishes.model;

public class ProductOrder extends Product {

	public ProductOrder(long id_product, Integer quantity, Integer id_user) {
		this.setId_product(id_product);
		this.quantity = quantity;
		this.id_user = id_user;
	}

	public ProductOrder(String name, Double price, Integer availability, Integer quantity,
			Integer id_user) {
		super (name, price, availability);
		this.quantity = quantity;
		this.id_user = id_user;
	}
	
	public ProductOrder() {
		
	}

	
	Integer quantity;
	
	Integer id_user;

	public Integer getId_user() {
		return id_user;
	}

	public void setId_user(Integer id_user) {
		this.id_user = id_user;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ProductOrder [quantity=" + quantity + ", id_user=" + id_user + "] - " + super.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id_user == null) ? 0 : id_user.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductOrder other = (ProductOrder) obj;
		if (id_user == null) {
			if (other.id_user != null)
				return false;
		} else if (!id_user.equals(other.id_user))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}
	
	
}
