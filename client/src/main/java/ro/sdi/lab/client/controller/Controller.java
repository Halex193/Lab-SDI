package ro.sdi.lab.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import ro.sdi.lab.core.model.dto.ClientGenre;
import ro.sdi.lab.core.model.dto.RentedMovieStatistic;
import ro.sdi.lab.web.converter.ClientGenreConverter;
import ro.sdi.lab.web.converter.RentedMovieStatisticConverter;
import ro.sdi.lab.web.dto.ClientGenresDto;
import ro.sdi.lab.web.dto.RentedMovieStatisticsDto;

@Service
public class Controller
{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RentedMovieStatisticConverter rentedMovieStatisticConverter;

    @Autowired
    private ClientGenreConverter clientGenreConverter;

    public static final String URL = "http://localhost:8080/api/statistics";
    public static final Logger log = LoggerFactory.getLogger(Controller.class);

    public Iterable<RentedMovieStatistic> getTop10RentedMovies()
    {
        RentedMovieStatisticsDto rentedMovieStatisticsDto = restTemplate.getForObject(
                URL + "/movies",
                RentedMovieStatisticsDto.class
        );
        assert rentedMovieStatisticsDto != null;
        List<RentedMovieStatistic> rentedMovieStatistics =
                rentedMovieStatisticsDto.getRentedMovieStatistics()
                                        .stream()
                                        .map(rentedMovieStatisticConverter::toModel)
                                        .collect(Collectors.toList());
        log.trace("Rented movie statistic: {}", rentedMovieStatistics);
        return rentedMovieStatistics;
    }

    /**
     * Retrieves the most rented genre for each client, or the empty string if the client did not rent any movies
     */
    public Iterable<ClientGenre> getClientGenres()
    {
        ClientGenresDto clientGenres = restTemplate.getForObject(
                URL + "/genres",
                ClientGenresDto.class
        );
        assert clientGenres != null;
        List<ClientGenre> clientGenreList =
                clientGenres.getClientGenres()
                           .stream()
                           .map(clientGenreConverter::toModel)
                           .collect(Collectors.toList());
        log.trace("Client genres: {}", clientGenreList);
        return clientGenreList;
    }
}
