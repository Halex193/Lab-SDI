package ro.sdi.lab.core.validation;

import ro.sdi.lab.core.exception.ValidatorException;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
