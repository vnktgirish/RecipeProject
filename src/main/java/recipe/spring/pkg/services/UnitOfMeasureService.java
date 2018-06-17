package recipe.spring.pkg.services;

import java.util.Set;

import recipe.spring.pkg.commands.UnitOfMeasureCommand;
import recipe.spring.pkg.domain.UnitOfMeasure;

public interface UnitOfMeasureService {
	Set<UnitOfMeasureCommand> listAllUoms();
}
