package ro.sdi.lab.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.model.dto.ClientGenre;
import ro.sdi.lab.core.model.dto.RentedMovieStatistic;
import ro.sdi.lab.core.repository.ClientRepository;
import ro.sdi.lab.core.repository.MovieRepository;

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

    public void logEntities()
    {
        /*log.debug("Clients with movies: " + clientRepository
                .findAll()
                .stream()
                .map(client -> String.format(
                        "%s - %s",
                        client.toString(),
                        client.getMovies().toString()
                ))
                .collect(Collectors.joining(",")));*/

        log.debug("Clients with movies: " + clientRepository
                          .findClientsWithMovies()
                          .stream()
                          .map(client -> String.format(
                                  "%s - %s",
                                  client.toString(),
                                  client.getMovies().toString()
                          ))
                          .collect(Collectors.joining(",")));

        log.debug("Clients with rentals: " + clientRepository
                          .findClientsWithRentals()
                          .stream()
                          .map(client -> String.format(
                                  "%s - %s",
                                  client.toString(),
                                  client.getRentals()
                                          .stream()
                                          .map(Rental::getTime)
                                          .map(LocalDateTime::toString)
                                          .collect(Collectors.joining(","))
                          ))
                          .collect(Collectors.joining(",")));

        log.debug("Movies with clients: " + movieRepository
                .findMoviesWithClients()
                .stream()
                .map(movie -> String.format(
                        "%s - %s",
                        movie.toString(),
                        movie.getClients().toString()
                ))
                .collect(Collectors.joining(",")));

        log.debug("Movies with rentals: " + movieRepository
                .findMoviesWithRentals()
                .stream()
                .map(movie -> String.format(
                        "%s - %s",
                        movie.toString(),
                        movie.getRentals()
                                .stream()
                                .map(Rental::getTime)
                                .map(LocalDateTime::toString)
                                .collect(Collectors.joining(","))
                ))
                .collect(Collectors.joining(",")));
    }

}
