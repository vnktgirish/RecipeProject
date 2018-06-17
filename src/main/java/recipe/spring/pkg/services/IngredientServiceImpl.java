package recipe.spring.pkg.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.media.jfxmedia.logging.Logger;

import lombok.extern.slf4j.Slf4j;
import recipe.spring.pkg.commands.IngredientCommand;
import recipe.spring.pkg.converters.IngredientCommandToIngredient;
import recipe.spring.pkg.converters.IngredientToIngredientCommand;
import recipe.spring.pkg.converters.RecipeCommandToRecipe;
import recipe.spring.pkg.converters.RecipeToRecipeCommand;
import recipe.spring.pkg.domain.Ingredient;
import recipe.spring.pkg.domain.Recipe;
import recipe.spring.pkg.repositories.RecipeRepository;
import recipe.spring.pkg.repositories.UnitOfMeasureRepository;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	public IngredientServiceImpl(RecipeRepository recipeRepository,
			IngredientToIngredientCommand ingredeintToIngredientCommand,
			UnitOfMeasureRepository unitOfMeasureRepository,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		super();
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredeintToIngredientCommand;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	public IngredientCommand findByIdAndIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			log.error("Requested recipe is not found.");
		}

		Recipe recipe = recipeOptional.get();
		Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

		if (!optionalIngredientCommand.isPresent()) {
			log.error("Ingredient not found: " + ingredientId);
		}
		return optionalIngredientCommand.get();
	}

	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
		if (!recipeOptional.isPresent()) {
			log.error("Reuquested recipe not found.");
			return new IngredientCommand();
		}
		Recipe recipe = recipeOptional.get();
		Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();
		if (optionalIngredient.isPresent()) {
			Ingredient ingredientFound = optionalIngredient.get();
			ingredientFound.setDescription(command.getDescription());
			ingredientFound.setAmount(command.getAmount());
			ingredientFound.setUom(
					unitOfMeasureRepository.findById(command.getUom().getId()).orElseThrow(RuntimeException::new));
		} else {
			//add new ingredient
			Ingredient ingredient = ingredientCommandToIngredient.convert(command);
			ingredient.setRecipe(recipe);
			recipe.addIngredient(ingredient);
		}
		Recipe savedRecipe = recipeRepository.save(recipe);

		Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();
		if (!savedIngredientOptional.isPresent()) {
			savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
					.filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
					.filter(ingredient -> ingredient.getUom().getId().equals(command.getUom().getId())).findFirst();
		}
		return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
	}

	@Override
	public void deleteById(long recipeId, long id) {
		log.debug("Deleting ingredient "+recipeId+": "+id);
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if(recipeOptional.isPresent()) {
			Recipe recipe = recipeOptional.get();
			log.debug("found recipe");
			Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(id)).findFirst();
			if(optionalIngredient.isPresent()) {
				log.debug("found ingredient");
				Ingredient ingredientToDelete = optionalIngredient.get();
				ingredientToDelete.setRecipe(null);
				recipe.getIngredients().remove(optionalIngredient.get());
				recipeRepository.save(recipe);
			}
		} else {
			log.debug("recipe id not found "+recipeId + ": "+id);
		}
	}

}
