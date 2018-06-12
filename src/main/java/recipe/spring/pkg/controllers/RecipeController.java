package recipe.spring.pkg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import recipe.spring.pkg.commands.RecipeCommand;
import recipe.spring.pkg.services.RecipeService;

@Controller
public class RecipeController {
	
	private RecipeService recipeService;
	
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id ,Model model) {
		try {
			model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "recipe/show";
		
	}
	
	@GetMapping
	@RequestMapping("recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/recipeform";
	}
	
	@PostMapping
	@RequestMapping("recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		return "redirect:/recipe/show/"+savedCommand.getId();
	}
	@GetMapping
	@RequestMapping("recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {
		recipeService.deleteById(Long.parseLong(id));
		return "redirect:/";
	}

}
