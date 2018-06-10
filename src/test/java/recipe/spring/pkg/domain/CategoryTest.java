package recipe.spring.pkg.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {
	
	Category category;
	
	@Before
	public void setUp() {
		System.out.println("Creating a new category object.");
		category = new Category();
	}

	@Test
	public void testGetId() {
		Long idvalue = 4L;
		category.setId(idvalue);
		assertEquals(idvalue, category.getId());
	}

	@Test
	public void testGetDescription() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRecipes() {
		fail("Not yet implemented");
	}

}
