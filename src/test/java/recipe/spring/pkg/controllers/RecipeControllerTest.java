package recipe.spring.pkg.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import recipe.spring.pkg.commands.RecipeCommand;
import recipe.spring.pkg.domain.Recipe;
import recipe.spring.pkg.exceptions.NotFoundException;
import recipe.spring.pkg.services.RecipeService;

public class RecipeControllerTest {

	@Mock
	RecipeService recipeService;
	RecipeController recipeController;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeController = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
	}

	@Test
	public void testGetRecipe() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		when(recipeService.findById(ArgumentMatchers.anyLong())).thenReturn(recipe);

		mockMvc.perform(get("/recipe/1/show")).andExpect(status().isOk()).andExpect(view().name("recipe/show"))
				.andExpect(model().attributeExists("recipe"));
	}

	public void testGetRecipeNotFound() throws Exception {
		when(recipeService.findById(ArgumentMatchers.anyLong())).thenThrow(NotFoundException.class);

		mockMvc.perform(get("/recipe/1/show")).andExpect(status().isNotFound()).andExpect(view().name("404error"));
	}

	@Test
	public void testNewRecipe() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		mockMvc.perform(get("/recipe/new")).andExpect(status().isOk()).andExpect(view().name("recipe/recipeform"));
	}

	@Test
	public void testSaveOrUpdateRecipe() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(2L);
		when(recipeService.saveRecipeCommand(ArgumentMatchers.any())).thenReturn(recipeCommand);
		mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "")
				.param("description", "some string")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/show"));
	}

	@Test
	public void testUpdateRecipe() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(ArgumentMatchers.anyLong())).thenReturn(recipeCommand);
		mockMvc.perform(get("/recipe/1/update")).andExpect(status().isOk()).andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void testDeleteRecipe() throws Exception {
		mockMvc.perform(get("/recipe/1/delete")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
		verify(recipeService, times(1)).deleteById(ArgumentMatchers.anyLong());

	}

}
