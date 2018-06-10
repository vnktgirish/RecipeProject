package recipe.spring.pkg.repositories;

import org.springframework.data.repository.CrudRepository;

import recipe.spring.pkg.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
	
}
