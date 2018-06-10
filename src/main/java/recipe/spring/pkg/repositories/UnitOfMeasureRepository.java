package recipe.spring.pkg.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import recipe.spring.pkg.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long>{
	Optional<UnitOfMeasure> findByDescription(String description);
}
