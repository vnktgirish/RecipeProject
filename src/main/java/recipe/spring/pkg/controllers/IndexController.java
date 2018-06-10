package recipe.spring.pkg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import recipe.spring.pkg.services.RecipeService;

@Controller
@Slf4j
public class IndexController {
	
	RecipeService recipeService;
		
	public IndexController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}


	@RequestMapping({"","/","/index"})
	public String getIndexPage(Model model) {
		log.debug("Getting an index page.");
		model.addAttribute("recipes", recipeService.getRecipeList());
		return "index";
	}
}
