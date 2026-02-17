package uk.co.mayden.shopping.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * TODO - Mocks some products.
 * @author Sarge
 */
@Configuration
class DataConfiguration implements CommandLineRunner {
	@Autowired
	private ProductRepository repository;

	@Override
	public void run(String... args) throws Exception {
		repository.save(new Product("Fresh Fruit", 1));
		repository.save(new Product("Vegetables", 2));
		repository.save(new Product("Low-fat Yoghurt", 3));
	}
}
