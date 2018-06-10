package recipe.spring.pkg.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import recipe.spring.pkg.domain.Category;
import recipe.spring.pkg.domain.Difficulty;
import recipe.spring.pkg.domain.Ingredient;
import recipe.spring.pkg.domain.Notes;
import recipe.spring.pkg.domain.Recipe;
import recipe.spring.pkg.domain.UnitOfMeasure;
import recipe.spring.pkg.repositories.CategoryRepository;
import recipe.spring.pkg.repositories.RecipeRepository;
import recipe.spring.pkg.repositories.UnitOfMeasureRepository;

@Component
@Slf4j
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private RecipeRepository recipeRepository;
	private CategoryRepository categoryRepository;
	private UnitOfMeasureRepository unitOfMeasureRepository;

	public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.recipeRepository = recipeRepository;
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	private List<Recipe> getRecipeList() {
		List<Recipe> recipes = new ArrayList<>(2);

		// get uom's
		Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");
		if (!eachUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM not found.");
		}
		Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
		if (!tableSpoonUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM not found.");
		}
		Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
		if (!teaSpoonUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM not found.");
		}
		Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");
		if (!dashUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM not found.");
		}
		Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");
		if (!pintUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM not found.");
		}
		Optional<UnitOfMeasure> pinchUomOptional = unitOfMeasureRepository.findByDescription("Pinch");
		if (!pinchUomOptional.isPresent()) {
			throw new RuntimeException("Expected UOM not found.");
		}

		// get optionals
		UnitOfMeasure eachUom = eachUomOptional.get();
		UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
		UnitOfMeasure teaSpoonUom = teaSpoonUomOptional.get();
		UnitOfMeasure dashUom = dashUomOptional.get();
		UnitOfMeasure pintUom = pintUomOptional.get();
		UnitOfMeasure pinchUom = pinchUomOptional.get();

		// get categories
		Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
		if (!americanCategoryOptional.isPresent()) {
			throw new RuntimeException("Expected category not found.");
		}
		Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
		if (!mexicanCategoryOptional.isPresent()) {
			throw new RuntimeException("Expected category not found.");
		}
		
		Category americanCategory = americanCategoryOptional.get();
		Category mexicanCategory = mexicanCategoryOptional.get();
		
		Recipe guacRecipe = new Recipe();
		guacRecipe.setDescription("Perfect Guacamole");
		guacRecipe.setPrepTime(10);
		guacRecipe.setCookTime(0);
		guacRecipe.setDifficulty(Difficulty.EASY);
		guacRecipe.setDirections("Here are the directions");
		
		Notes guacNotes = new Notes();
		guacNotes.setRecipeNotes("This part is for notes.");
		guacNotes.setRecipe(guacRecipe);
		// guacRecipe.setNotes(guacNotes);
		
		guacRecipe.addIngredient((new Ingredient("ripe avocados", new BigDecimal(2), eachUom)));
		guacRecipe.addIngredient((new Ingredient("Kushar Salt", new BigDecimal(2), teaSpoonUom)));
		guacRecipe.addIngredient((new Ingredient("fresh lime juice", new BigDecimal(2), tableSpoonUom)));
		guacRecipe.addIngredient((new Ingredient("serano chillies", new BigDecimal(2), eachUom)));
		guacRecipe.addIngredient((new Ingredient("cilantro", new BigDecimal(2), tableSpoonUom)));
		
		guacRecipe.getCategories().add(americanCategory);
		guacRecipe.getCategories().add(mexicanCategory);
		recipes.add(guacRecipe);
		
		Recipe chickenRecipe = new Recipe();
		chickenRecipe.setDescription("Andhra Spicy Chicken");
		chickenRecipe.setPrepTime(10);
		chickenRecipe.setCookTime(0);
		chickenRecipe.setDifficulty(Difficulty.EASY);
		chickenRecipe.setDirections("Here are the directions");
		
		Notes chickenNotes = new Notes();
		chickenNotes.setRecipeNotes("Chicken recipe notes.");
		chickenNotes.setRecipe(guacRecipe);
		// guacRecipe.setNotes(guacNotes);
		
		chickenRecipe.addIngredient((new Ingredient("Onions", new BigDecimal(4), eachUom)));
		chickenRecipe.addIngredient((new Ingredient("Kushar Salt", new BigDecimal(2), pinchUom)));
		chickenRecipe.addIngredient((new Ingredient("fresh lime juice", new BigDecimal(2), tableSpoonUom)));
		chickenRecipe.addIngredient((new Ingredient("serano chillies", new BigDecimal(2), eachUom)));
		chickenRecipe.addIngredient((new Ingredient("Chicken", new BigDecimal(1), eachUom)));
		
		chickenRecipe.getCategories().add(americanCategory);
		chickenRecipe.getCategories().add(mexicanCategory);
		recipes.add(chickenRecipe);
		return recipes;
	}

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		recipeRepository.saveAll(getRecipeList());
		log.debug("Loading bootstrap data.");
	}
}
