package ro.sdi.lab.core.validation;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import ro.sdi.lab.core.exception.ValidatorException;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;

    default void hibernateValidation(T entity, javax.validation.Validator validator)
    {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() == 0) return;
        String errorMessage = constraintViolations.stream()
                                                  .map(ConstraintViolation::getMessage)
                                                  .collect(Collectors.joining(";"));
        throw new ValidatorException(errorMessage);
    }
}
