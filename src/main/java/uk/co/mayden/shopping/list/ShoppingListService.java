package uk.co.mayden.shopping.list;

import java.util.*;
import java.util.function.Predicate;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import uk.co.mayden.shopping.product.*;

/**
 * The <i>shopping list service</i> orchestrates changes to a shopping list.
 * @author Sarge
 */
@Service
public class ShoppingListService {
	private final ProductRepository productRepository;
	private final ShoppingListRepository shoppingListRepository;

	/**
	 * Constructor.
	 * @param productRepository				Products DAO
	 * @param shoppingListRepository		Shopping list DAO
	 */
	public ShoppingListService(ProductRepository productRepository, ShoppingListRepository shoppingListRepository) {
		this.productRepository = productRepository;
		this.shoppingListRepository = shoppingListRepository;
	}

	/**
	 * Retrieves or creates the shopping list for the current user.
	 * @return Shopping list
	 */
	public ShoppingList getShoppingList() {
		final String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return shoppingListRepository
				.findByUsername(username)
				.orElseGet(() -> createShoppingList(username));
	}

	/**
	 * @param username User
	 * @return New shopping list
	 */
	private ShoppingList createShoppingList(String username) {
		final var shopping = new ShoppingList(username);
		shoppingListRepository.save(shopping);
		return shopping;
	}

	/**
	 * Determines the <i>available</> products, i.e. those not selected by the user.
	 * @param selected Selected products in the shopping list
	 * @return Available products
	 */
	public List<Product> getAvailableProducts(Collection<Product> selected) {
		return productRepository
				.findAll()
				.stream()
				.filter(Predicate.not(selected::contains))
				.toList();
	}

	/**
	 * Looks up a product.
	 * @param id Product code
	 * @return Product
	 */
	public Product product(long id) {
		return productRepository.getReferenceById(id);
	}

	/**
	 * Adds a product to the shopping list.
	 * @param id Product to add
	 */
	public void add(long id) {
		final ShoppingList shopping = getShoppingList();
		final Product product = product(id);
		shopping.getProducts().add(product);
		shoppingListRepository.save(shopping);
	}

	/**
	 * Removes a product from the shopping list.
	 * @param id Product to remove
	 */
	public void remove(long id) {
		final ShoppingList shopping = getShoppingList();
		final Product product = product(id);
		shopping.getProducts().remove(product);
		shopping.getPurchased().remove(product);
		shoppingListRepository.save(shopping);
	}

	/**
	 * Sets the spending limit on the given shopping list.
	 * @param limit Spending limit
	 */
	public void setSpendingLimit(int limit) {
		final ShoppingList shopping = getShoppingList();
		shopping.setSpendingLimit(limit);
		shoppingListRepository.save(shopping);
	}

	/**
	 * Crosses off a product on the shopping list.
	 * @param id Product
	 */
	public void purchase(long id) {
		final ShoppingList shopping = getShoppingList();
		final Product product = product(id);
		shopping.getPurchased().add(product);
		shoppingListRepository.save(shopping);
	}
}
