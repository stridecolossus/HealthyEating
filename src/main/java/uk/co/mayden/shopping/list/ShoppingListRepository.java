package uk.co.mayden.shopping.list;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
	/**
	 * Looks up the shopping list for the given user.
	 * @param username User name
	 * @return Shopping list
	 */
	Optional<ShoppingList> findByUsername(String username);
}
