package ro.sdi.lab.core.validation;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import ro.sdi.lab.core.exception.ValidatorException;
import ro.sdi.lab.core.model.Rental;

public class RentalValidator implements Validator<Rental>
{
    @Autowired
    private javax.validation.Validator validator;

    /**
     * This function validates an entity which is supposed to be a rental
     * In order for a rental to be valid, its IDs must be non-negative numbers and the rental date
     * should be less than today's date
     *
     * @param entity: the supposed-to-be rental
     * @throws ValidatorException : thrown in case a validation error occurs with all the occured errors
     */
    @Override
    public void validate(Rental entity) throws ValidatorException
    {
        Optional.of(entity)
                .filter(rental -> rental.getId().getClientId() >= 0)
                .orElseThrow(() -> new ValidatorException(String.format(
                        "Rental %s has an invalid client ID",
                        entity.toString()
                )));
        Optional.of(entity)
                .filter(rental -> rental.getId().getMovieId() >= 0)
                .orElseThrow(() -> new ValidatorException(String.format(
                        "Rental %s has an invalid movie ID",
                        entity.toString()
                )));
        hibernateValidation(entity, validator);
    }
}
