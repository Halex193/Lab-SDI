package ro.sdi.lab.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import ro.sdi.lab.core.exception.AlreadyExistingElementException;
import ro.sdi.lab.core.exception.DateTimeInvalidException;
import ro.sdi.lab.core.exception.ElementNotFoundException;
import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.model.Rental;

@Service
public class RentalService
{
    public static final Logger log = LoggerFactory.getLogger(RentalService.class);
    private final ClientService clientService;
    private final MovieService movieService;

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public RentalService(ClientService clientService, MovieService movieService)
    {
        this.clientService = clientService;
        this.movieService = movieService;
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
        Movie movie = movieService
                .findOne(movieId)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Movie %d does not exist",
                        movieId
                )));
        Client client = clientService
                .findOne(clientId)
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
        clientService.clientRepository.save(client);
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

        Movie movie = movieService
                .findOne(movieId)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Movie %d does not exist",
                        movieId
                )));
        Client client = clientService
                .findOne(clientId)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Client %d does not exist",
                        clientId
                )));
        if (!client.getMovies().stream().anyMatch((m) -> m == movie))
        {
            throw new ElementNotFoundException(String.format(
                    "Rental of movie %d and client %d does not exist",
                    movieId,
                    clientId
            ));
        }
        client.deleteMovie(movie);
        clientService.clientRepository.save(client);
    }

    /**
     * This function returns an iterable collection of the current state of the rentals in the repository
     *
     * @return all: an iterable collection of rentals
     */
    public List<Rental> getRentals()
    {
        log.trace("Retrieving all rentals");
        return clientService.clientRepository.getAllRentals();
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
    public void updateRental(int movieId, int clientId, String time)
    {
        checkRentalID(movieId, clientId);
        Rental rental;
        try
        {
            rental = new Rental(movieId, clientId, LocalDateTime.parse(time, formatter));
        }
        catch (DateTimeParseException e)
        {
            throw new DateTimeInvalidException("Date and time invalid");
        }
        rentalValidator.validate(rental);
        log.trace("Updating rental {}", rental);
        rentalRepository.update(rental)
                        .orElseThrow(() -> new ElementNotFoundException(String.format(
                                "Rental of movie %d and client %d does not exist",
                                movieId,
                                clientId
                        )));
    }

    public Iterable<Rental> filterRentalsByMovieName(String name)
    {
        log.trace("Filtering rentals by the movie name {}", name);
        String regex = ".*" + name + ".*";
        return StreamSupport.stream(rentalRepository.findAll().spliterator(), false)
                            .filter(rental -> movieService.findOne(rental.getId().getMovieId())
                                                          .filter(t -> t.getName()
                                                                        .matches(regex))
                                                          .isPresent())
                            .collect(Collectors.toUnmodifiableList());
    }

    public List<Rental> getRentals(String dateFilter, Sort sort, int page, int pageSize)
    {
        return rentalRepository.findAll(
                filterByDate(dateFilter),
                PageRequest.of(page, pageSize, sort)
        );
    }

    private Specification<Rental> filterByDate(String dateFilter)
    {
        if (dateFilter.isEmpty()) return (root, query, criteriaBuilder) ->
                criteriaBuilder.and();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.<LocalDateTime>get(
                        "time"), LocalDateTime.parse(dateFilter, formatter));
    }

}
