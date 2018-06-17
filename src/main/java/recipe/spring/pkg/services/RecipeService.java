package recipe.spring.pkg.services;

import java.util.Set;

import recipe.spring.pkg.commands.RecipeCommand;
import recipe.spring.pkg.domain.Recipe;

public interface RecipeService {
	Set<Recipe> getRecipeList();
	Recipe findById(Long id);
	
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	RecipeCommand findCommandById(Long id);
	void deleteById(Long id);
}
