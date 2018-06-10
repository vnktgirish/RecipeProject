package recipe.spring.pkg.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import recipe.spring.pkg.domain.Recipe;
import recipe.spring.pkg.repositories.RecipeRepository;

public class RecipeServiceImplTest {
	
	RecipeService recipeService;
	@Mock
	RecipeRepository recipeRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository);
	}

	@Test
	public void testGetRecipeList() {
		Recipe recipe = new Recipe();
		HashSet recipeData = new HashSet<>();
		recipeData.add(recipe);
		
		when(recipeService.getRecipeList()).thenReturn(recipeData);
		Set<Recipe> recipes = recipeService.getRecipeList();
		assertEquals(recipeData.size(), 1);
		verify(recipeRepository, times(1)).findAll();
		
	}

}
