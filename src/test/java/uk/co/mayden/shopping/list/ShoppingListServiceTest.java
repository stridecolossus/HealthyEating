package uk.co.mayden.shopping.list;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.*;

import uk.co.mayden.shopping.product.*;

class ShoppingListServiceTest {
	private ShoppingListService service;
	private ProductRepository productRepository;
	private ShoppingListRepository shoppingListRepository;
	private ShoppingList shopping;
	private Product product;

	@BeforeEach
	void before() {
		product = new Product("product", 3);
		shopping = new ShoppingList();
		productRepository = mock(ProductRepository.class);
		shoppingListRepository = mock(ShoppingListRepository.class);
		service = new ShoppingListService(productRepository, shoppingListRepository);

		when(shoppingListRepository.findByUsername("shopper")).thenReturn(Optional.of(shopping));
		when(productRepository.getReferenceById(3L)).thenReturn(product);

		mockNastySpringAuthentication();
	}

	private static void mockNastySpringAuthentication() {
		final var authentication = mock(Authentication.class);
		when(authentication.getName()).thenReturn("shopper");
		SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
	}

	@DisplayName("A shopping list is implicitly linked to the shopper")
	@Test
	void retrieve() {
		assertEquals(shopping, service.getShoppingList());
	}

	@DisplayName("A new shopping list is created for a shopper as required")
	@Test
	void create() {
		when(shoppingListRepository.findByUsername("shopper")).thenReturn(Optional.empty());
		assertNotNull(service.getShoppingList());
	}

	@DisplayName("A product that has not been added to the list...")
	@Nested
	class Available {
		@DisplayName("is available for selection")
		@Test
		void available() {
			when(productRepository.findAll()).thenReturn(List.of(product));
			assertEquals(List.of(product), service.getAvailableProducts(Set.of()));
		}

		@DisplayName("can be added to the list")
		@Test
		void add() {
			service.add(3);
			assertEquals(List.of(product), shopping.getProducts());
			verify(shoppingListRepository).save(shopping);
		}
	}

	@DisplayName("A product that has been added to the list...")
	@Nested
	class Added {
		@BeforeEach
		void before() {
			service.add(3);
		}

		@DisplayName("is no longer available for selection")
		@Test
		void available() {
			final Product other = new Product("other", 2);
			when(productRepository.findAll()).thenReturn(List.of(product, other));
			assertEquals(List.of(product), service.getAvailableProducts(Set.of(other)));
		}

		@DisplayName("can be removed from the list")
		@Test
		void remove() {
			service.remove(3);
			assertEquals(List.of(), shopping.getProducts());
		}

		@DisplayName("can be crossed off to mark it as purchased")
		@Test
		void purchase() {
			service.purchase(3);
			assertEquals(List.of(product), shopping.getPurchased());
		}
	}

	@DisplayName("The shopper can set a spending limit on the total cost of the shopping list")
	@Test
	void limit() {
		service.setSpendingLimit(42);
		assertEquals(42, shopping.getSpendingLimit());
		verify(shoppingListRepository).save(shopping);
	}
}
