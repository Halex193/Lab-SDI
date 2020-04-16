package ro.sdi.lab.core.validation;

import java.util.Optional;

import ro.sdi.lab.core.exception.ValidatorException;
import ro.sdi.lab.core.model.Movie;

public class MovieValidator implements Validator<Movie>
{
    /**
     * This function validates an entity which is supposed to be a movie
     * In order for a movie to be valid, its ID must be an non-negative number and its name must contain
     * only alphabetic characters or digits and its rating must be an integer between 0 and 100
     *
     * @param entity: the supposed-to-be movie
     * @throws ValidatorException : thrown in case a validation error occurs with all the occured errors
     */
    @Override
    public void validate(Movie entity) throws ValidatorException {
        Optional.of(entity).filter(movie -> movie.getId() >= 0).orElseThrow(() -> new ValidatorException(String.format("movie %d has an invalid ID", entity.getId())));
        Optional.of(entity).filter(movie -> movie.getName().matches("^[a-zA-Z0-9]+$")).orElseThrow(() -> new ValidatorException(String.format("movie %d has an invalid name", entity.getId())));
        Optional.of(entity).filter(movie -> {
            int rating = movie.getRating();
            return rating >= 0 && rating <= 100;
        }).orElseThrow(() -> new ValidatorException(String.format("movie %d has an invalid name", entity.getId())));
    }
}
