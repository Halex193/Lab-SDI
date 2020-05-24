package ro.sdi.lab.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import ro.sdi.lab.core.exception.AlreadyExistingElementException;
import ro.sdi.lab.core.exception.DateTimeInvalidException;
import ro.sdi.lab.core.exception.ElementNotFoundException;
import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.repository.ClientRepository;
import ro.sdi.lab.core.repository.MovieRepository;
import ro.sdi.lab.core.repository.Repository;

@Service
public class RentalService
{
    public static final Logger log = LoggerFactory.getLogger(RentalService.class);
    private final ClientRepository clientRepository;
    private final MovieRepository movieRepository;
    public DateTimeFormatter formatter;

    public RentalService(
            ClientRepository clientRepository,
            MovieRepository movieRepository,
            DateTimeFormatter formatter
    )
    {
        this.clientRepository = clientRepository;
        this.movieRepository = movieRepository;
        this.formatter = formatter;
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
        if (client.getMovies().stream().noneMatch((m) -> m == movie))
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

    /**
     * This function returns an iterable collection of the current state of the rentals in the repository
     *
     * @return all: an iterable collection of rentals
     */
    public List<Rental> getRentals()
    {
        log.trace("Retrieving all rentals");
        return movieRepository.getAllRentals();
    }

    /**
     * This function updates a rental based on the movie ID and client ID with its new time
     *
     * @param movieId:  the ID of the movie
     * @param clientId: the ID of the client
     * @param time:     date and time - the object of updation
     * @throws ElementNotFoundException if the movie or client does not exist in the repository or
     *                                  if the rental is nowhere to be found
     */
    @Transactional
    public void updateRental(int movieId, int clientId, String time)
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
        log.trace("Updating rental {}", rental);

        client.updateRentalTime(movie, dateTime);
        clientRepository.save(client);
    }
}
