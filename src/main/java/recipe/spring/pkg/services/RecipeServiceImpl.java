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
import recipe.spring.pkg.repositories.RecipeRepository;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {
	
	private RecipeRepository recipeRepository;
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
	public Recipe findById(long id) throws Exception {
		Optional<Recipe> recipeOptional = recipeRepository.findById(id);
		
		if (!recipeOptional.isPresent()) {
			throw new Exception("Recipe not found");
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
		try {
			return recipeToRecipeCommand.convert(findById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
	}

	@Override
	public void deleteById(long id) {
		recipeRepository.deleteById(id);
	} 

}
