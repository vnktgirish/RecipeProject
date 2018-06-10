package recipe.spring.pkg.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import recipe.spring.pkg.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>{
	Optional<Category> findByDescription(String description);
}
