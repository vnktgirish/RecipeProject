package recipe.spring.pkg.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import recipe.spring.pkg.domain.Recipe;
import recipe.spring.pkg.services.RecipeService;
import org.mockito.ArgumentMatchers;

public class IndexControllerTest {
	
	@Mock
	RecipeService recipeService;
	@Mock
	Model model;
	IndexController indexController;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		indexController = new IndexController(recipeService);
	}
	
	@Test
	public void testMockMvc() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}
	
	@Test
	public void testGetIndexPage() {
		Set<Recipe> recipes = new HashSet<>();
		recipes.add(new Recipe());
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		recipes.add(recipe);
		when(recipeService.getRecipeList()).thenReturn(recipes);
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
		
		String view = indexController.getIndexPage(model);
		assertEquals("index", view);
		verify(recipeService, times(1)).getRecipeList();
		verify(model, times(1)).addAttribute(ArgumentMatchers.eq("recipes"), argumentCaptor.capture());
		Set<Recipe> recipeTest = argumentCaptor.getValue();
		assertEquals(2, recipeTest.size());
	}

}
