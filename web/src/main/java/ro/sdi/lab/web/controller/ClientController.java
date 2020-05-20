package ro.sdi.lab.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import ro.sdi.lab.core.exception.AlreadyExistingElementException;
import ro.sdi.lab.core.exception.ElementNotFoundException;
import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.service.ClientService;
import ro.sdi.lab.web.converter.ClientConverter;
import ro.sdi.lab.web.dto.ClientDto;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class ClientController
{
    public static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientConverter clientConverter;


    @RequestMapping(value = "/clients", method = GET)
    public List<ClientDto> getClients()
    {
        Iterable<Client> clients = clientService.getClients();
        log.trace("Get clients: {}", clients);
        return clientConverter.toDtos(clients);
    }

    @RequestMapping(value = "/clients", method = POST)
    public ResponseEntity<?> addClient(@RequestBody ClientDto clientDto)
    {
        Client client = clientConverter.toModel(clientDto);
        try
        {
            clientService.addClient(client.getId(), client.getName());
        }
        catch (AlreadyExistingElementException e)
        {
            log.trace("Client {} already exists", client);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        log.trace("Client {} added", client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/{id}", method = DELETE)
    public ResponseEntity<?> deleteClient(@PathVariable int id)
    {
        try
        {
            clientService.deleteClient(id);
        }
        catch (ElementNotFoundException e)
        {
            log.trace("Client with id {} could not be deleted", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.trace("Client with id {} was deleted", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/clients/{id}", method = PUT)
    public ResponseEntity<?> updateClient(@PathVariable int id, @RequestBody ClientDto clientDto)
    {
        Client client = clientConverter.toModel(clientDto);
        try
        {
            clientService.updateClient(id, client.getName());
        }
        catch (ElementNotFoundException e)
        {
            log.trace("Client with id {} could not be updated", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.trace("Client with id {} was updated: {}", id, client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/filter/{name}", method = GET)
    public List<ClientDto> filterClientsByName(@PathVariable String name)
    {
        return clientConverter.toDtos(clientService.filterClientsByName(name));
    }

    @RequestMapping(value = "/clients/{page}", method = GET)
    public List<ClientDto> getClients(
            @PathVariable int page,
            @RequestParam(defaultValue = "3") int pageSize,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "") String filter
            )
    {
        Sort sortObject = Sort.by(sort);
        if (order.equals("asc")) sortObject = sortObject.ascending();
        if (order.equals("desc")) sortObject = sortObject.descending();

        Iterable<Client> clients = clientService.getClients(filter, sortObject, page, pageSize);
        log.trace("Get clients page {} with filter {}, sort by {}, order {}: {}", page, filter, sort, order, clients);
        return clientConverter.toDtos(clients);
    }
}
