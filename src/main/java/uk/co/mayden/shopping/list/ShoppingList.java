package uk.co.mayden.shopping.list;

import java.util.*;

import jakarta.persistence.*;
import uk.co.mayden.shopping.product.Product;

/**
 * A <i>shopping list</i> composes the product information for a given user.
 * Note this is a <b>single</b> instance with a one-to-one relationship to the user (indexed by the username).
 * @author Sarge
 */
@Entity
public class ShoppingList {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String username;

	@OneToMany
	private List<Product> products = new ArrayList<>();

	@OneToMany
	private List<Product> purchased = new ArrayList<>();

	private int spendingLimit;

	ShoppingList() {
	}

	public ShoppingList(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public Collection<Product> getProducts() {
		return products;
	}

	public Collection<Product> getPurchased() {
		return purchased;
	}

	public int getSpendingLimit() {
		return spendingLimit;
	}

	public void setSpendingLimit(int spendingLimit) {
		this.spendingLimit = spendingLimit;
	}
}
