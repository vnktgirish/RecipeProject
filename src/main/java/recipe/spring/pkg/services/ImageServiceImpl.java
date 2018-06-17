package recipe.spring.pkg.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import recipe.spring.pkg.domain.Recipe;
import recipe.spring.pkg.repositories.RecipeRepository;

@Service
public class ImageServiceImpl implements ImageService {

	private final RecipeRepository recipeRepository;

	public ImageServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void saveImageFile(Long recipeId, MultipartFile multipartFile) {

		try {
			Recipe recipe = recipeRepository.findById(recipeId).get();
			Byte[] byteObjects = new Byte[multipartFile.getBytes().length];
			
			int i =0;
			
			for(byte b : multipartFile.getBytes()) {
				byteObjects[i++] = b;
			}
			
			recipe.setImages(byteObjects);
			
			recipeRepository.save(recipe);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
