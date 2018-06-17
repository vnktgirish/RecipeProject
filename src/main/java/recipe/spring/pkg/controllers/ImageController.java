package recipe.spring.pkg.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.mockito.internal.util.io.IOUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import recipe.spring.pkg.commands.RecipeCommand;
import recipe.spring.pkg.services.ImageService;
import recipe.spring.pkg.services.RecipeService;

@Controller
public class ImageController {

	private RecipeService recipeService;
	private ImageService imageService;

	public ImageController(ImageService imageService, RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
		this.imageService = imageService;
	}
	
	@GetMapping("recipe/{id}/image")
	public String showUploadForm(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/imageuploadform";
	}
	
	@PostMapping("recipe/{id}/image")
	public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile multipartFile) {
		imageService.saveImageFile(Long.valueOf(id), multipartFile);
		return "redirect:/recipe/"+id+"/show";
	}
	
	@GetMapping("recipe/{id}/recipeimage")
	public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) {
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));
		
		byte[] byteArray = new byte[recipeCommand.getImage().length];
		int i = 0;
		for (Byte b : recipeCommand.getImage()) {
			byteArray[i++] = b;
		}
		response.setContentType("image/jpeg");
		InputStream is = new ByteArrayInputStream(byteArray);
		try {
			IOUtils.copy(is, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
