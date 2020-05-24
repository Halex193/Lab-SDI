package ro.sdi.lab.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.model.dto.ClientGenre;
import ro.sdi.lab.core.model.dto.RentedMovieStatistic;
import ro.sdi.lab.core.repository.Repository;
import ro.sdi.lab.core.validation.Validator;

@org.springframework.stereotype.Service
public class Service
{
    public static final Logger log = LoggerFactory.getLogger(Service.class);

    Repository<Integer, Client> clientRepository;
    Repository<Integer, Movie> movieRepository;
    Validator<Client> clientValidator;
    Validator<Movie> movieValidator;
    Validator<Rental> rentalValidator;

    public Service(
            Repository<Integer, Client> clientRepository,
            Repository<Integer, Movie> movieRepository,
            Validator<Client> clientValidator,
            Validator<Movie> movieValidator,
            Validator<Rental> rentalValidator
    )
    {
        this.clientRepository = clientRepository;
        this.movieRepository = movieRepository;
        this.clientValidator = clientValidator;
        this.movieValidator = movieValidator;
        this.rentalValidator = rentalValidator;
    }

    public Iterable<RentedMovieStatistic> getTop10RentedMovies()
    {
        return Collections.emptyList();
    }

    /**
     * Retrieves the most rented genre for each client, or the empty string if the client did not rent any movies
     */
    public Iterable<ClientGenre> getClientGenres()
    {
        return Collections.emptyList();
    }

}
