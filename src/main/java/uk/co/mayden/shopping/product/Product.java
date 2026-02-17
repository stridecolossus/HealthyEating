package uk.co.mayden.shopping.product;

import jakarta.persistence.*;

/**
 * A <i>product</i> is a grocery item that can be added to a shopping list.
 * @author Sarge
 */
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private Integer price;

	Product() {
	}

	/**
	 * Constructor.
	 * @param name		Product name
	 * @param price		Price (GBP)
	 */
	public Product(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}
}
