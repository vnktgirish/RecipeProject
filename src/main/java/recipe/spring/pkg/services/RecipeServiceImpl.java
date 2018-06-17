package recipe.spring.pkg.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import recipe.spring.pkg.commands.RecipeCommand;
import recipe.spring.pkg.converters.RecipeCommandToRecipe;
import recipe.spring.pkg.converters.RecipeToRecipeCommand;
import recipe.spring.pkg.domain.Recipe;
import recipe.spring.pkg.exceptions.NotFoundException;
import recipe.spring.pkg.repositories.RecipeRepository;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {
	
	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
		
	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> getRecipeList() {
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
		return recipeSet;
	}
	 
	@Override
	public Recipe findById(Long id) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(id);
		
		if (!recipeOptional.isPresent()) {
			log.info("Recipe not found");
			throw new NotFoundException("Recipe Not Found");
		}
		
		return recipeOptional.get();
	}

	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		log.debug("Saved RecipeId: "+savedRecipe.getId());
		return recipeToRecipeCommand.convert(savedRecipe);
	}

	@Override
	@Transactional
	public RecipeCommand findCommandById(Long id) {
			return recipeToRecipeCommand.convert(findById(id));
	}

	@Override
	public void deleteById(Long id) {
		recipeRepository.deleteById(id);
	} 

}
