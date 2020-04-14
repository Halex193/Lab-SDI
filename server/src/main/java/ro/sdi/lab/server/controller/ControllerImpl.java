package ro.sdi.lab.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import ro.sdi.lab.common.controller.Controller;
import ro.sdi.lab.common.model.Client;
import ro.sdi.lab.common.model.Movie;
import ro.sdi.lab.common.model.Rental;
import ro.sdi.lab.common.model.dto.ClientGenre;
import ro.sdi.lab.common.model.dto.RentedMovieStatistic;
import ro.sdi.lab.server.repository.Repository;
import ro.sdi.lab.server.validation.Validator;

@Service
public class ControllerImpl implements Controller
{
    public static final Logger log = LoggerFactory.getLogger(ControllerImpl.class);

    Repository<Integer, Client> clientRepository;
    Repository<Integer, Movie> movieRepository;
    Repository<Rental.RentalID, Rental> rentalRepository;
    Validator<Client> clientValidator;
    Validator<Movie> movieValidator;
    Validator<Rental> rentalValidator;

    public ControllerImpl(
            Repository<Integer, Client> clientRepository,
            Repository<Integer, Movie> movieRepository,
            Repository<Rental.RentalID, Rental> rentalRepository,
            Validator<Client> clientValidator,
            Validator<Movie> movieValidator,
            Validator<Rental> rentalValidator
    )
    {
        this.clientRepository = clientRepository;
        this.movieRepository = movieRepository;
        this.rentalRepository = rentalRepository;
        this.clientValidator = clientValidator;
        this.movieValidator = movieValidator;
        this.rentalValidator = rentalValidator;
    }

    @Override
    public Iterable<RentedMovieStatistic> getTop10RentedMovies()
    {
        log.trace("Retrieving top 10 rented movies");
        Stream<Rental> rentalStream = StreamSupport.stream(rentalRepository.findAll().spliterator(), false);
        Map<String, Long> occurrenceMap = rentalStream
                .map(rental -> movieRepository.findOne(rental.getId().getMovieId()).get().getName())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        return occurrenceMap
                .entrySet()
                .stream()
                .map(entry -> new RentedMovieStatistic(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingLong(RentedMovieStatistic::getNumberOfRentals).reversed())
                .limit(10)
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Retrieves the most rented genre for each client, or the empty string if the client did not rent any movies
     */
    @Override
    public Iterable<ClientGenre> getClientGenres()
    {
        log.trace("Retrieving client favorite genres");
        return StreamSupport.stream(clientRepository.findAll().spliterator(), false)
                            .map(client -> new ClientGenre(client,
                                                           StreamSupport.stream(rentalRepository.findAll().spliterator(), false)
                                                                        .filter(rental -> rental.getId().getClientId() == client.getId())
                                                                        .map(this::getMovieGenre)
                                                                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                                                        .entrySet()
                                                                        .stream()
                                                                        .max(Map.Entry.comparingByValue())
                                                                        .map(Map.Entry::getKey)
                                                                        .orElse("")
                                 )
                            )
                            .collect(Collectors.toUnmodifiableList());
    }

    private String getMovieGenre(Rental rental)
    {
        return StreamSupport.stream(movieRepository.findAll().spliterator(), false)
                            .filter(movie -> movie.getId() == rental.getId().getMovieId())
                            .findAny()
                            .map(Movie::getGenre)
                            .orElse("");
    }
}
