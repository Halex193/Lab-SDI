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
import java.util.Collections;
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
    public DateTimeFormatter formatter;

    public RentalService(
            ClientService clientService,
            MovieService movieService,
            DateTimeFormatter formatter
    )
    {
        this.clientService = clientService;
        this.movieService = movieService;
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

    }

    /**
     * This function returns an iterable collection of the current state of the rentals in the repository
     *
     * @return all: an iterable collection of rentals
     */
    public List<Rental> getRentals()
    {
        return Collections.emptyList();
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

    }

    public Iterable<Rental> filterRentalsByMovieName(String name)
    {
        return Collections.emptyList();
    }

    public List<Rental> getRentals(String dateFilter, Sort sort, int page, int pageSize)
    {
        return Collections.emptyList();
    }


}
