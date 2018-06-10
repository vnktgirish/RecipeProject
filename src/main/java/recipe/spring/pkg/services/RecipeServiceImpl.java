package recipe.spring.pkg.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import recipe.spring.pkg.domain.Recipe;
import recipe.spring.pkg.repositories.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService {
	
	private RecipeRepository recipeRepository;
		
	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}

	@Override
	public Set<Recipe> getRecipeList() {
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
		return recipeSet;
	}

}
