package recipe.spring.pkg.services;

import java.util.Set;

import recipe.spring.pkg.commands.RecipeCommand;
import recipe.spring.pkg.domain.Recipe;

public interface RecipeService {
	Set<Recipe> getRecipeList();
	Recipe findById(long id) throws Exception;
	
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	RecipeCommand findCommandById(Long valueOf);
	void deleteById(long id);
}
