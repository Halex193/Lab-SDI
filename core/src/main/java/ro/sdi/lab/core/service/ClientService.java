package ro.sdi.lab.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ro.sdi.lab.core.exception.AlreadyExistingElementException;
import ro.sdi.lab.core.exception.DateTimeInvalidException;
import ro.sdi.lab.core.exception.ElementNotFoundException;
import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.repository.ClientRepository;
import ro.sdi.lab.core.repository.MovieRepository;
import ro.sdi.lab.core.repository.Repository;
import ro.sdi.lab.core.validation.Validator;

@Service
public class ClientService
{
    public static final Logger log = LoggerFactory.getLogger(ClientService.class);

    ClientRepository clientRepository;
    Validator<Client> clientValidator;
    MovieRepository movieRepository;
    public DateTimeFormatter formatter;

    public ClientService(
            ClientRepository clientRepository,
            Validator<Client> clientValidator,
            MovieRepository movieRepository,
            DateTimeFormatter formatter
    )
    {
        this.clientRepository = clientRepository;
        this.clientValidator = clientValidator;
        this.movieRepository = movieRepository;
        this.formatter = formatter;
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

        clientRepository.findById(id).ifPresent(
                (item) ->
                {
                    throw new AlreadyExistingElementException(String.format(
                            "Client %d already exists",
                            id
                    ));
                });
        clientRepository.save(client);
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
        if (clientRepository.findById(id).isEmpty())
        {
            throw new ElementNotFoundException(String.format(
                    "Client %d does not exist",
                    id
            ));
        }
        clientRepository.deleteById(id);
    }

    /**
     * This function returns an iterable collection of the current state of the clients in the repository
     *
     * @return all: an iterable collection of clients
     */
    public List<Client> getClients()
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
    @Transactional
    public void updateClient(int id, String name)
    {
        Client client = new Client(id, name);
        clientValidator.validate(client);
        log.trace("Updating client {}", client);

        Client oldClient = clientRepository
                .findById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        String.format(
                                "Client %d does not exist",
                                id
                        )));
        oldClient.setId(client.getId());
        oldClient.setName(client.getName());
        clientRepository.save(oldClient);
    }

    public Iterable<Client> filterClientsByName(String name)
    {
        log.trace("Filtering clients by the name {}", name);
        String regex = ".*" + name + ".*";
        return clientRepository.findAll().stream()
                .filter(client -> client.getName().matches(regex))
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<Client> findOne(int clientId)
    {
        return clientRepository.findById(clientId);
    }

    /**
     * This function adds a rental to the repository
     *
     * @param movieId:  the ID of the movie
     * @param clientId: the ID of the client
     * @param time:     date and time of rental
     * @throws ElementNotFoundException        if movie or client doesn't exist in the repository
     * @throws AlreadyExistingElementException if the rental already exists in the repository
     * @throws DateTimeInvalidException        if the date and time cannot be parsed
     */
    @Transactional
    public void addRental(int movieId, int clientId, String time)
    {
        Movie movie = movieRepository
                .findById(movieId)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Movie %d does not exist",
                        movieId
                )));
        Client client = clientRepository
                .getClientAndMovies(clientId)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Client %d does not exist",
                        clientId
                )));

        Rental rental;
        LocalDateTime dateTime;
        try
        {
            if (time.equals("now"))
                dateTime = LocalDateTime.now();
            else
                dateTime = LocalDateTime.parse(time, formatter);
            rental = new Rental(movie, client, dateTime);
        }
        catch (DateTimeParseException e)
        {
            throw new DateTimeInvalidException("Date and time invalid");
        }
        log.trace("Adding rental {}", rental);
        if(client.getMovies().stream().anyMatch(m -> m.getId().equals(movie.getId())))
        {
            throw new AlreadyExistingElementException("Rental already exists");
        }
        client.rentMovie(movie, dateTime);
        clientRepository.save(client);
    }

    /**
     * This function deletes a rental from the repository
     *
     * @param movieId:  the ID of the movie
     * @param clientId: the ID of the client
     * @throws ElementNotFoundException if the movie, client don't exist of if the rental itself doesn't exist in the repository
     */
    @Transactional
    public void deleteRental(int movieId, int clientId)
    {
        log.trace("Removing rental with id {} {}", movieId, clientId);

        Movie movie = movieRepository
                .findById(movieId)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Movie %d does not exist",
                        movieId
                )));
        Client client = clientRepository
                .getClientAndMovies(clientId)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Client %d does not exist",
                        clientId
                )));
        if (client.getMovies().stream().noneMatch(m -> m.getId().equals(movie.getId())))
        {
            throw new ElementNotFoundException(String.format(
                    "Rental of movie %d and client %d does not exist",
                    movieId,
                    clientId
            ));
        }
        client.deleteMovie(movie);
        clientRepository.save(client);
    }


}
