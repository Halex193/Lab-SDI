package ro.sdi.lab.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import ro.sdi.lab.core.exception.AlreadyExistingElementException;
import ro.sdi.lab.core.exception.DateTimeInvalidException;
import ro.sdi.lab.core.exception.ElementNotFoundException;
import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.web.converter.RentalConverter;
import ro.sdi.lab.web.dto.RentalsDto;

@Service
public class RentalController
{
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RentalConverter rentalConverter;

    public static final String URL = "http://localhost:8080/api/rentals";
    public static final Logger log = LoggerFactory.getLogger(RentalController.class);
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");


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
        Rental rental;
        try
        {
            rental = new Rental(movieId, clientId, LocalDateTime.parse(time, formatter));
        }
        catch (DateTimeParseException e)
        {
            throw new DateTimeInvalidException("Datetime invalid!");
        }
        try
        {
            restTemplate.postForEntity(
                    URL,
                    rental,
                    Object.class
            );
            log.trace("Rental {} added", rental);
        }
        catch (RestClientException e)
        {
            log.trace("Rental {} could not be added", rental);
            throw new AlreadyExistingElementException("Rental could not be added!");
        }
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
        try
        {
            restTemplate.delete(String.format("%s/%d-%d", URL, movieId, clientId));
            log.trace("Rental with id {}-{} deleted", movieId, clientId);
        }
        catch (RestClientException e)
        {
            log.trace("Rental with id {}-{} was not deleted", movieId, clientId);
            throw new ElementNotFoundException("Rental does not exist!");
        }
    }

    /**
     * This function returns an iterable collection of the current state of the rentals in the repository
     *
     * @return all: an iterable collection of rentals
     */
    public Iterable<Rental> getRentals()
    {
        RentalsDto rentalsDto = restTemplate.getForObject(URL, RentalsDto.class);
        assert rentalsDto != null;
        List<Rental> rentals = rentalsDto.getRentals()
                                         .stream()
                                         .map(rentalConverter::toModel)
                                         .collect(Collectors.toList());
        log.trace("Fetched rentals {}", rentals);
        return rentals;
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
        Rental rental;
        try
        {
            rental = new Rental(movieId, clientId, LocalDateTime.parse(time, formatter));
        }
        catch (DateTimeParseException e)
        {
            throw new DateTimeInvalidException("Datetime invalid!");
        }
        try
        {
            restTemplate.put(String.format("%s/%d-%d", URL, movieId, clientId), rental);
            log.trace("Updated rental {}", rental);
        }
        catch (RestClientException e)
        {
            log.trace("Rental {} was not updated", rental);
            throw new ElementNotFoundException("Rental not found!");
        }
    }

    public Iterable<Rental> filterRentalsByMovieName(String name)
    {
        RentalsDto rentalsDto = restTemplate.getForObject(URL + "/filter/" + name, RentalsDto.class);
        assert rentalsDto != null;
        List<Rental> rentals = rentalsDto.getRentals()
                                         .stream()
                                         .map(rentalConverter::toModel)
                                         .collect(Collectors.toList());
        log.trace("Rentals filtered by name {}: {}", name, rentals);
        return rentals;
    }
}
