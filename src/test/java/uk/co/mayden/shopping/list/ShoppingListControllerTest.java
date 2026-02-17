package uk.co.mayden.shopping.list;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.ui.ExtendedModelMap;

import uk.co.mayden.shopping.product.Product;

class ShoppingListControllerTest {
	private ShoppingListController controller;
	private ShoppingListService service;
	private ShoppingList shopping;

	@BeforeEach
	void before() {
		shopping = new ShoppingList("shopper");
		service = mock(ShoppingListService.class);
		when(service.getShoppingList()).thenReturn(shopping);
		controller = new ShoppingListController(service);
	}

	@DisplayName("The user can view the selected and available products in their shopping list")
	@Test
	void list() {
		final var product = new Product("product", 3);
		shopping.getProducts().add(product);
		shopping.setSpendingLimit(42);

		final var model = new ExtendedModelMap();
		assertEquals("shopping-list", controller.list(model));
		assertEquals("shopper", model.getAttribute("shopper"));
		assertEquals(shopping, model.getAttribute("shopping"));
		assertEquals(List.of(), model.getAttribute("available"));
		assertEquals(3, model.getAttribute("total"));
		assertEquals(42, model.getAttribute("spendingLimit"));
		assertNull(model.getAttribute("warn"));
	}

	@DisplayName("The user can view the selected and available products in their shopping list")
	@Test
	void warning() {
		final var product = new Product("product", 3);
		shopping.getProducts().add(product);
		shopping.setSpendingLimit(2);

		final var model = new ExtendedModelMap();
		controller.list(model);
		assertNotNull(model.getAttribute("warn"));
	}

	@DisplayName("The user can add an available product to their shopping list")
	@Test
	void add() {
		assertEquals("redirect:/list", controller.add(3));
		verify(service).add(3);
	}

	@DisplayName("The user can remove a selected product from their shopping list")
	@Test
	void remove() {
		assertEquals("redirect:/list", controller.remove(3));
		verify(service).remove(3);
	}

	@DisplayName("The user can set a spending limit on the overall cost of their shopping list")
	@Test
	void limit() {
		assertEquals("redirect:/list", controller.limit("42"));
		verify(service).setSpendingLimit(42);
	}

	@DisplayName("The spending limit must be zero-or-more")
	@Test
	void limitInvalid() {
		assertEquals("redirect:/list?error", controller.limit("cobblers"));
		assertEquals("redirect:/list?error", controller.limit("-1"));
		verifyNoInteractions(service);
	}
}
