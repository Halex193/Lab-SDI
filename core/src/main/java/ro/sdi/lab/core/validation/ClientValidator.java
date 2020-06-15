package ro.sdi.lab.core.validation;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import ro.sdi.lab.core.exception.ValidatorException;
import ro.sdi.lab.core.model.Client;

public class ClientValidator implements Validator<Client>
{
    @Autowired
    private javax.validation.Validator validator;

    /**
     * This function validates an entity which is supposed to be a client
     * In order for a client to be valid, its ID must be an non-negative number and its name must contain
     * only alphabetic characters
     *
     * @param entity: the supposed-to-be client
     * @throws ValidatorException : thrown in case a validation error occurs with all the occured errors
     */
    @Override
    public void validate(Client entity) throws ValidatorException
    {
        Optional.of(entity)
                .filter(client -> client.getId() >= 0)
                .orElseThrow(() -> new ValidatorException(String.format(
                        "Client %d has an invalid ID",
                        entity.getId()
                )));
        hibernateValidation(entity, validator);
    }
}
