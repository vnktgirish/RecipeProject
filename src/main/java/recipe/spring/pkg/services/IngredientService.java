package recipe.spring.pkg.services;

import recipe.spring.pkg.commands.IngredientCommand;

public interface IngredientService {
	IngredientCommand findByIdAndIngredientId(Long recipeId, Long ingredientId);
	IngredientCommand saveIngredientCommand(IngredientCommand command);
	void deleteById(long recipeId, long id);
}
