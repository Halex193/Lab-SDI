package ro.sdi.lab.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.model.dto.ClientGenre;
import ro.sdi.lab.core.model.dto.RentedMovieStatistic;
import ro.sdi.lab.core.repository.ClientRepository;
import ro.sdi.lab.core.repository.MovieRepository;
import ro.sdi.lab.core.repository.Repository;
import ro.sdi.lab.core.validation.Validator;

@org.springframework.stereotype.Service
public class Service
{
    public static final Logger log = LoggerFactory.getLogger(Service.class);

    private final ClientRepository clientRepository;
    private final MovieRepository movieRepository;

    public Service(
            ClientRepository clientRepository,
            MovieRepository movieRepository
    )
    {
        this.clientRepository = clientRepository;
        this.movieRepository = movieRepository;
    }

    public List<RentedMovieStatistic> getTop10RentedMovies()
    {
        return movieRepository.getTop10RentedMovies();
    }

    /**
     * Retrieves the most rented genre for each client, or the empty string if the client did not rent any movies
     */
    public List<ClientGenre> getClientGenres()
    {
        return clientRepository.getClientGenres();
    }

}
