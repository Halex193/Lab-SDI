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

import java.time.format.DateTimeFormatter;
import java.util.List;

import ro.sdi.lab.core.exception.AlreadyExistingElementException;
import ro.sdi.lab.core.exception.ElementNotFoundException;
import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.service.RentalService;
import ro.sdi.lab.web.converter.RentalConverter;
import ro.sdi.lab.web.dto.RentalDto;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class RentalController
{
    public static final Logger log = LoggerFactory.getLogger(RentalController.class);

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RentalConverter rentalConverter;


    @RequestMapping(value = "/rentals", method = GET)
    public List<RentalDto> getRentals()
    {
        Iterable<Rental> rentals = rentalService.getRentals();
        log.trace("Get rentals: {}", rentals);
        return rentalConverter.toDtos(rentals);
    }

    @RequestMapping(value = "/rentals", method = POST)
    public ResponseEntity<?> addRental(@RequestBody RentalDto rentalDto)
    {
        Rental rental = rentalConverter.toModel(rentalDto);
        try
        {
            rentalService.addRental(
                    rental.getId().getMovieId(),
                    rental.getId().getClientId(),
                    formatter.format(rental.getTime())
            );
        }
        catch (AlreadyExistingElementException e)
        {
            log.trace("Rental {} already exists", rental);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        log.trace("Rental {} added", rental);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/{movieId}-{clientId}", method = DELETE)
    public ResponseEntity<?> deleteRental(@PathVariable int clientId, @PathVariable int movieId)
    {
        try
        {
            rentalService.deleteRental(movieId, clientId);
        }
        catch (ElementNotFoundException e)
        {
            log.trace(
                    "Rental with id {} could not be deleted",
                    new Rental.RentalID(movieId, clientId)
            );
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.trace("Rental with id {} was deleted", new Rental.RentalID(movieId, clientId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/{movieId}-{clientId}", method = PUT)
    public ResponseEntity<?> updateRental(
            @PathVariable int clientId,
            @PathVariable int movieId,
            @RequestBody RentalDto rentalDto
    )
    {
        Rental rental = rentalConverter.toModel(rentalDto);
        try
        {
            rentalService.updateRental(movieId, clientId, formatter.format(rental.getTime()));
        }
        catch (ElementNotFoundException e)
        {
            log.trace(
                    "Rental with id {} could not be updated",
                    new Rental.RentalID(movieId, clientId)
            );
            e.printStackTrace();
        }
        log.trace(
                "Rental with id {} was updated: {}",
                new Rental.RentalID(movieId, clientId),
                rental
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/filter/{name}", method = GET)
    public List<RentalDto> filterRentalsByMovieName(String name)
    {
        return rentalConverter.toDtos(rentalService.filterRentalsByMovieName(name));
    }
}
