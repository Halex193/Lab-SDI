package ro.sdi.lab.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import ro.sdi.lab.core.exception.AlreadyExistingElementException;
import ro.sdi.lab.core.exception.ElementNotFoundException;
import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.service.ClientService;
import ro.sdi.lab.web.converter.ClientConverter;
import ro.sdi.lab.web.dto.ClientsDto;

@Service
public class ClientController
{
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ClientConverter clientConverter;

    public static final String URL = "http://localhost:8080/api/clients";
    public static final Logger log = LoggerFactory.getLogger(ClientController.class);

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
        try
        {
            restTemplate.postForEntity(
                    URL,
                    client,
                    Object.class
            );
            log.trace("Client {} added", client);
        }
        catch (RestClientException e)
        {
            log.trace("Client {} already exists", client);
            throw new AlreadyExistingElementException("Client already exists!");
        }
    }

    /**
     * This function removes a client from the repository based on their ID
     *
     * @param id: the ID of the client
     * @throws ElementNotFoundException if the client isn't found in the repository based on their ID
     */
    public void deleteClient(int id)
    {
        try
        {
            restTemplate.delete(URL + "/" + id);
            log.trace("Client with id {} deleted", id);
        }
        catch (RestClientException e)
        {
            log.trace("Client with id {} was not deleted", id);
            throw new ElementNotFoundException("Client does not exist!");
        }
    }

    /**
     * This function returns an iterable collection of the current state of the clients in the repository
     *
     * @return all: an iterable collection of clients
     */
    public Iterable<Client> getClients()
    {
        ClientsDto clientsDto = restTemplate.getForObject(URL, ClientsDto.class);
        assert clientsDto != null;
        List<Client> clients = clientsDto.getClients()
                                         .stream()
                                         .map(clientConverter::toModel)
                                         .collect(Collectors.toList());
        log.trace("Fetched clients {}", clients);
        return clients;
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
        try
        {
            restTemplate.put(URL + "/" + id, client);
            log.trace("Updated client {}", client);
        }
        catch (RestClientException e)
        {
            log.trace("Client {} was not updated", client);
            throw new ElementNotFoundException("Client not found!");
        }
    }

    public Iterable<Client> filterClientsByName(String name)
    {
        ClientsDto clientsDto = restTemplate.getForObject(URL + "/filter/" + name, ClientsDto.class);
        assert clientsDto != null;
        List<Client> clients = clientsDto.getClients()
                                         .stream()
                                         .map(clientConverter::toModel)
                                         .collect(Collectors.toList());
        log.trace("Filtered clients by name {}: {}", name, clients);
        return clients;
    }
}
