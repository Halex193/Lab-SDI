package ro.sdi.lab.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import ro.sdi.lab.core.exception.AlreadyExistingElementException;
import ro.sdi.lab.core.exception.DateTimeInvalidException;
import ro.sdi.lab.core.exception.ElementNotFoundException;
import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.repository.Repository;
import ro.sdi.lab.core.validation.Validator;

@Service
public class RentalService
{
    public static final Logger log = LoggerFactory.getLogger(RentalService.class);

    private final ClientService clientService;
    private final MovieService movieService;
    Repository<Rental.RentalID, Rental> rentalRepository;
    Validator<Rental> rentalValidator;
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public RentalService(
            ClientService clientService,
            MovieService movieService,
            Repository<Rental.RentalID, Rental> rentalRepository,
            Validator<Rental> rentalValidator
    )
    {
        this.clientService = clientService;
        this.movieService = movieService;
        this.rentalRepository = rentalRepository;
        this.rentalValidator = rentalValidator;
        setupCascadeDeletion();
    }

    private void setupCascadeDeletion()
    {
        clientService.setEntityDeletedListener(
                client -> StreamSupport
                        .stream(rentalRepository.findAll().spliterator(), false)
                        .filter(rental -> rental.getId().getClientId() == client.getId())
                        .forEach(rental -> rentalRepository.delete(rental.getId()))
        );

        movieService.setEntityDeletedListener(
                movie -> StreamSupport
                        .stream(rentalRepository.findAll().spliterator(), false)
                        .filter(rental -> rental.getId().getMovieId() == movie.getId())
                        .forEach(rental -> rentalRepository.delete(rental.getId()))
        );
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
    public void addRental(int movieId, int clientId, String time)
    {
        checkRentalID(movieId, clientId);
        Rental rental;
        try
        {
            LocalDateTime dateTime;
            if (time.equals("now"))
                dateTime = LocalDateTime.now();
            else
                dateTime = LocalDateTime.parse(time, formatter);
            rental = new Rental(movieId, clientId, dateTime);
        }
        catch (DateTimeParseException e)
        {
            throw new DateTimeInvalidException("Date and time invalid");
        }
        rentalValidator.validate(rental);
        log.trace("Adding rental {}", rental);
        rentalRepository.save(rental)
                        .ifPresent(opt ->
                                   {
                                       throw new AlreadyExistingElementException(String.format(
                                               "Rental of movie %d and client %d already exists",
                                               movieId,
                                               clientId
                                       ));
                                   });
    }

    private void checkRentalID(int movieId, int clientId)
    {
        movieService.findOne(movieId)
                    .orElseThrow(() -> new ElementNotFoundException(String.format(
                            "Movie %d does not exist",
                            movieId
                    )));
        clientService.findOne(clientId)
                     .orElseThrow(() -> new ElementNotFoundException(String.format(
                             "Client %d does not exist",
                             clientId
                     )));
    }

    /**
     * This function deletes a rental from the repository
     *
     * @param movieId:  the ID of the movie
     * @param clientId: the ID of the client
     * @throws ElementNotFoundException if the movie, client don't exist of if the rental itself doesn't exist in the repository
     */
    public void deleteRental(int movieId, int clientId)
    {
        log.trace("Removing rental with id {} {}", movieId, clientId);
        checkRentalID(movieId, clientId);
        rentalRepository.delete(new Rental.RentalID(movieId, clientId))
                        .orElseThrow(() -> new ElementNotFoundException(String.format(
                                "Rental of movie %d and client %d does not exist",
                                movieId,
                                clientId
                        )));
    }

    /**
     * This function returns an iterable collection of the current state of the rentals in the repository
     *
     * @return all: an iterable collection of rentals
     */
    public Iterable<Rental> getRentals()
    {
        log.trace("Retrieving all rentals");
        return rentalRepository.findAll();
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
}
