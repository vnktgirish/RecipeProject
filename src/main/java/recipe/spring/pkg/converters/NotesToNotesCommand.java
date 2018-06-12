package recipe.spring.pkg.converters;


import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Synchronized;
import recipe.spring.pkg.commands.NotesCommand;
import recipe.spring.pkg.domain.Notes;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand>{

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if (source == null) {
            return null;
        }

        final NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getRecipeNotes());
        return notesCommand;
    }
}
