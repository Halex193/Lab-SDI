package ro.sdi.lab.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import ro.sdi.lab.core.exception.AlreadyExistingElementException;
import ro.sdi.lab.core.exception.ElementNotFoundException;
import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.repository.Repository;
import ro.sdi.lab.core.validation.Validator;

@Service
public class ClientController
{
    public static final Logger log = LoggerFactory.getLogger(ClientController.class);

    Repository<Integer, Client> clientRepository;
    Validator<Client> clientValidator;
    EntityDeletedListener<Client> entityDeletedListener = null;

    public ClientController(
            Repository<Integer, Client> clientRepository,
            Validator<Client> clientValidator
    )
    {
        this.clientRepository = clientRepository;
        this.clientValidator = clientValidator;
    }

    public void setEntityDeletedListener(EntityDeletedListener<Client> entityDeletedListener)
    {
        this.entityDeletedListener = entityDeletedListener;
    }

    /**
     * This function adds a client to the repository
     *
     * @param id:   the ID of the client
     * @param name: the name of the client
     * @throws AlreadyExistingElementException if the client (the ID) is already there
     */
    public void addClient(int id, String name)
    {
        Client client = new Client(id, name);
        clientValidator.validate(client);
        log.trace("Adding client {}", client);
        clientRepository
                .save(client)
                .ifPresent(opt ->
                           {
                               throw new AlreadyExistingElementException(String.format(
                                       "Client %d already exists",
                                       id
                               ));
                           });
    }

    /**
     * This function removes a client from the repository based on their ID
     *
     * @param id: the ID of the client
     * @throws ElementNotFoundException if the client isn't found in the repository based on their ID
     */
    public void deleteClient(int id)
    {
        log.trace("Removing client with id {}", id);
        clientRepository
                .delete(id)
                .ifPresentOrElse(
                        entity -> Optional
                                .ofNullable(entityDeletedListener)
                                .ifPresent(listener -> listener.onEntityDeleted(entity)),
                        () ->
                        {
                            throw new ElementNotFoundException(String.format(
                                    "Client %d does not exist",
                                    id
                            ));
                        }
                );
    }

    /**
     * This function returns an iterable collection of the current state of the clients in the repository
     *
     * @return all: an iterable collection of clients
     */
    public Iterable<Client> getClients()
    {
        log.trace("Retrieving all clients");
        return clientRepository.findAll();
    }

    /**
     * This function updated a client based on their ID with a new name
     *
     * @param id:   the client's ID
     * @param name: the new name of the client
     * @throws ElementNotFoundException if the client isn't found in the repository based on their ID
     */
    public void updateClient(int id, String name)
    {
        Client client = new Client(id, name);
        clientValidator.validate(client);
        log.trace("Updating client {}", client);
        clientRepository
                .update(client)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Client %d does not exist",
                        id
                )));
    }

    public Iterable<Client> filterClientsByName(String name)
    {
        log.trace("Filtering clients by the name {}", name);
        String regex = ".*" + name + ".*";
        return StreamSupport
                .stream(clientRepository.findAll().spliterator(), false)
                .filter(client -> client.getName().matches(regex))
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<Client> findOne(int clientId)
    {
        return clientRepository.findOne(clientId);
    }
}
