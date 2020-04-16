package ro.sdi.lab.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.service.ClientService;
import ro.sdi.lab.web.converter.ClientConverter;
import ro.sdi.lab.web.dto.ClientDto;
import ro.sdi.lab.web.dto.ClientsDto;

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


    @RequestMapping(value = "/clients", method = POST)
    public ResponseEntity<?> addClient(@RequestBody ClientDto clientDto)
    {
        Client client = clientConverter.toModel(clientDto);
        clientService.addClient(client.getId(), client.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/{id}", method = DELETE)
    public ResponseEntity<?> deleteClient(@PathVariable int id)
    {
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/clients", method = GET)
    public ClientsDto getClients()
    {
        return new ClientsDto(clientConverter.toDtos(clientService.getClients()));
    }

    @RequestMapping(value = "/clients/{id}", method = PUT)
    public ResponseEntity<?> updateClient(@PathVariable int id, @RequestBody ClientDto clientDto)
    {
        clientService.updateClient(id, clientConverter.toModel(clientDto).getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/filter/{name}", method = GET)
    public ClientsDto filterClientsByName(@PathVariable String name)
    {
        return new ClientsDto(clientConverter.toDtos(clientService.filterClientsByName(name)));
    }
}
