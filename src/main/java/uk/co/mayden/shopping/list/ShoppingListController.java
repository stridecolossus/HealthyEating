package uk.co.mayden.shopping.list;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uk.co.mayden.shopping.product.Product;

/**
 * Page controller for viewing and editing a shopping list.
 * @author Sarge
 */
@Controller
public class ShoppingListController {
	private final ShoppingListService service;

	/**
	 * Constructor.
	 * @param service Shopping list service
	 */
	public ShoppingListController(ShoppingListService service) {
		this.service = service;
	}

	/**
	 * Views the shopping list.
	 * @param model Page model
	 * @return View name
	 */
	@GetMapping("/list")
	public String list(Model model) {
		// Retrieve/create shopping list
		final ShoppingList shopping = service.getShoppingList();

		// Calculate total cost of this shopping list
		final int total = shopping
				.getProducts()
				.stream()
				.mapToInt(Product::getPrice)
				.sum();

		// Determine available products
		final List<Product> available = service.getAvailableProducts(shopping.getProducts());

		// Build model
		model
				.addAttribute("shopper", shopping.getUsername())
				.addAttribute("shopping", shopping)
				.addAttribute("total", total)
				.addAttribute("spendingLimit", shopping.getSpendingLimit())
				.addAttribute("available", available);

		// Warn if spending limit exceeded
		final int limit = shopping.getSpendingLimit();
		if((limit > 0) && (total > limit)) {
			model.addAttribute("warn", "spending.limit.exceeded");
		}

		return "shopping-list";
	}

	/**
	 * Adds an available product to the shopping list.
	 * @param id Product to add
	 * @return View name
	 */
	@GetMapping("/list/{id}/add")
	public String add(@PathVariable("id") long id) {
		service.add(id);
		return "redirect:/list";
	}

	/**
	 * Removes a product from the shopping list, returning it to the available set.
	 * @param id Product code to remove
	 * @return View name
	 */
	@GetMapping("/list/{id}/remove")
	public String remove(@PathVariable("id") long id) {
		service.remove(id);
		return "redirect:/list";
	}

	/**
	 * Sets the spending limit for the shopping list.
	 * @param spendingLimit Spending limit
	 */
	@PostMapping("/spending-limit")
	public String limit(@RequestParam("spendingLimit") String spendingLimit) {
		// TODO - Set the error 'reason' as a request parameter and translate/handle on page accordingly
		try {
			final int limit = Integer.parseInt(spendingLimit);
			if(limit < 0) {
				return "redirect:/list?error";
			}
			service.setSpendingLimit(limit);
		}
		catch(NumberFormatException e) {
			return "redirect:/list?error";
		}

		return "redirect:/list";
	}
}
