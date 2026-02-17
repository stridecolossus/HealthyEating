package uk.co.mayden.shopping.tracker;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uk.co.mayden.shopping.list.*;
import uk.co.mayden.shopping.product.Product;

/**
 * The <i>shopping tracker</i> allows the user to <i>cross off</i> purchased items on their shopping list.
 * @author Sarge
 */
@Controller
public class ShoppingTracker {
	private final ShoppingListService service;

	/**
	 * Constructor.
	 * @param service Shopping list service
	 */
	public ShoppingTracker(ShoppingListService service) {
		this.service = service;
	}

	/**
	 * Lists the remaining products.
	 * @param model View model
	 * @return View
	 */
	@GetMapping("/tracker")
	public String tracker(Model model) {
		// Enumerate the remaining products to be crossed off
		final ShoppingList shopping = service.getShoppingList();
		final List<Product> remaining = new ArrayList<>(shopping.getProducts());
		remaining.removeAll(shopping.getPurchased());

		// Build view model
		model.addAttribute("shopper", shopping.getUsername());
		model.addAttribute("products", remaining);
		model.addAttribute("purchased", shopping.getPurchased());

		return "shopping-tracker";
	}

	/**
	 * Crosses off a product on the shopping list.
	 * @param id Product
	 */
	@GetMapping("/tracker/{id}/purchase")
	public String purchase(@PathVariable("id") long id) {
		service.purchase(id);
		return "redirect:/tracker";
	}
}
