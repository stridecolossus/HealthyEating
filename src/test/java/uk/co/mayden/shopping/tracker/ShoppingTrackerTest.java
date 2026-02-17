package uk.co.mayden.shopping.tracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.ui.ExtendedModelMap;

import uk.co.mayden.shopping.list.*;
import uk.co.mayden.shopping.product.Product;
import uk.co.mayden.shopping.tracker.ShoppingTracker;
class ShoppingTrackerTest {
	private ShoppingTracker tracker;
	private ShoppingListService service;
	private ShoppingList shopping;
	private Product product;

	@BeforeEach
	void before() {
		product = new Product("product", 1);
		shopping = new ShoppingList("shopper");
		shopping.getProducts().add(product);
		service = mock(ShoppingListService.class);
		when(service.getShoppingList()).thenReturn(shopping);
		tracker = new ShoppingTracker(service);
	}

	@Test
	void list() {
		final var model = new ExtendedModelMap();
		assertEquals("shopping-tracker", tracker.tracker(model));
		assertEquals("shopper", model.getAttribute("shopper"));
		assertEquals(List.of(product), model.getAttribute("products"));
		assertEquals(List.of(), model.getAttribute("purchased"));
	}

	@Test
	void purchase() {
		assertEquals("redirect:/tracker", tracker.purchase(3));
	}

	@Test
	void marked() {
		shopping.getPurchased().add(product);

		final var model = new ExtendedModelMap();
		tracker.tracker(model);
		assertEquals(List.of(), model.getAttribute("products"));
		assertEquals(List.of(product), model.getAttribute("purchased"));
	}
}
