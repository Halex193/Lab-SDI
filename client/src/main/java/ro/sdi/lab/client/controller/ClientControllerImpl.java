package ro.sdi.lab.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sdi.lab.common.controller.ClientController;
import ro.sdi.lab.common.model.Client;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class ClientControllerImpl implements FutureClientController {
    public static final Logger log = LoggerFactory.getLogger(ClientControllerImpl.class);

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private ClientController clientController;

    @Override
    public Future<Void> addClient(int id, String name) {
        Callable<Void> callable = () ->
        {
            log.trace("Sending request: add client id={}, name={}", id, name);
            clientController.addClient(id, name);
            log.trace("Received response: added client {},{}", id, name);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> deleteClient(int id) {
        Callable<Void> callable = () ->
        {
            log.trace("Sending request: delete client id={}", id);
            clientController.deleteClient(id);
            log.trace("Received response: deleted client id={}", id);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Client>> getClients() {
        Callable<Iterable<Client>> callable = () ->
        {
            log.trace("Sending request: get all clients");
            Iterable<Client> clients = clientController.getClients();
            log.trace("Received response: get all clients");
            return clients;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> updateClient(int id, String name) {
        Callable<Void> callable = () ->
        {
            log.trace("Sending request: update client id={}, name={}", id, name);
            clientController.updateClient(id, name);
            log.trace("Received response: update client id={}, name={}", id, name);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Client>> filterClientsByName(String name) {
        Callable<Iterable<Client>> callable = () ->
        {
            log.trace("Sending request: filter clients by name={}", name);
            Iterable<Client> clients = clientController.filterClientsByName(name);
            log.trace("Received response: filtered clients by name={}", name);
            return clients;
        };
        return executorService.submit(callable);
    }
}
