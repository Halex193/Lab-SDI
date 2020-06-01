package ro.sdi.lab.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;

import ro.sdi.lab.core.exception.AlreadyExistingElementException;
import ro.sdi.lab.core.exception.ElementNotFoundException;
import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.service.ClientService;
import ro.sdi.lab.core.service.MovieService;
import ro.sdi.lab.core.service.Service;
import ro.sdi.lab.web.converter.ClientGenreConverter;
import ro.sdi.lab.web.converter.RentalConverter;
import ro.sdi.lab.web.converter.RentedMovieStatisticConverter;
import ro.sdi.lab.web.dto.ClientGenreDto;
import ro.sdi.lab.web.dto.RentalDto;
import ro.sdi.lab.web.dto.RentedMovieStatisticDto;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class Controller
{
    public static final Logger log = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private Service service;

    @Autowired
    private RentedMovieStatisticConverter rentedMovieStatisticConverter;

    @Autowired
    private ClientGenreConverter clientGenreConverter;

    @RequestMapping(value = "/statistics/movies", method = RequestMethod.GET)
    public List<RentedMovieStatisticDto> getTop10RentedMovies()
    {
        return rentedMovieStatisticConverter.toDtos(service.getTop10RentedMovies());
    }

    @RequestMapping(value = "/statistics/genres", method = RequestMethod.GET)
    public List<ClientGenreDto> getClientGenres()
    {
        return clientGenreConverter.toDtos(service.getClientGenres());
    }

    @RequestMapping(value = "/statistics/log", method = RequestMethod.POST)
    public ResponseEntity<?> logEntities()
    {
        service.logEntities();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Autowired
    private ClientService clientService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private RentalConverter rentalConverter;


    @RequestMapping(value = "/rentals", method = GET)
    public List<RentalDto> getRentals()
    {
        Iterable<Rental> rentals = movieService.getRentals();
        log.trace("Get rentals: {}", rentals);
        return rentalConverter.toDtos(rentals);
    }

    @RequestMapping(value = "/rentals", method = POST)
    public ResponseEntity<?> addRental(@RequestBody RentalDto rentalDto)
    {
        try
        {
            clientService.addRental(
                    rentalDto.getMovieId(),
                    rentalDto.getClientId(),
                    rentalDto.getTime()
            );
        }
        catch (AlreadyExistingElementException e)
        {
            log.trace("Rental {}-{} already exists", rentalDto.getMovieId(), rentalDto.getClientId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        log.trace("Rental {}-{} added", rentalDto.getMovieId(), rentalDto.getClientId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/{movieId}-{clientId}", method = DELETE)
    public ResponseEntity<?> deleteRental(@PathVariable int clientId, @PathVariable int movieId)
    {
        try
        {
            clientService.deleteRental(movieId, clientId);
        }
        catch (ElementNotFoundException e)
        {
            log.trace(
                    "Rental with id {}-{} could not be deleted",
                    movieId, clientId
            );
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.trace("Rental with id {}-{} was deleted", movieId, clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/{movieId}-{clientId}", method = PUT)
    public ResponseEntity<?> updateRental(
            @PathVariable int clientId,
            @PathVariable int movieId,
            @RequestBody RentalDto rentalDto
    )
    {
        try
        {
            movieService.updateRental(movieId, clientId, rentalDto.getTime());
        }
        catch (ElementNotFoundException e)
        {
            log.trace(
                    "Rental with id {}-{} could not be updated",
                    movieId, clientId
            );
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.trace(
                "Rental with id {}-{} was updated: {}",
                movieId, clientId, rentalDto
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
