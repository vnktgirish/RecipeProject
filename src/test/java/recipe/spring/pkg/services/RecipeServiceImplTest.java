package recipe.spring.pkg.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.repository.CrudRepository;

import recipe.spring.pkg.domain.Recipe;
import recipe.spring.pkg.repositories.RecipeRepository;

public class RecipeServiceImplTest {
	
	RecipeService recipeService;
	@Mock
	RecipeRepository recipeRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository, null, null);
	}
	
	@Test
	public void testGetRecipeById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(recipeOptional);
		Recipe recipeReturned = recipeService.findById(1L);
		assertNotNull("Null recipe recevied", recipeReturned);
		verify(recipeRepository, times(1)).findById(ArgumentMatchers.anyLong());
		verify(recipeRepository, never()).findAll();
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
