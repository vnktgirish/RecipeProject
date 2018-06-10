package recipe.spring.pkg.services;

import java.util.Set;

import recipe.spring.pkg.domain.Recipe;

public interface RecipeService {
	Set<Recipe> getRecipeList();
}
