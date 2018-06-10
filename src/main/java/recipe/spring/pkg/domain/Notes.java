package recipe.spring.pkg.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(exclude = {"recipe"})
@Data
public class Notes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	@OneToOne
	private Recipe recipe;
	@Lob
	private String recipeNotes;

}
