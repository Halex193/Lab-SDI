package ro.sdi.lab.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.sdi.lab.core.service.Service;
import ro.sdi.lab.web.converter.ClientGenreConverter;
import ro.sdi.lab.web.converter.RentedMovieStatisticConverter;
import ro.sdi.lab.web.dto.ClientGenresDto;
import ro.sdi.lab.web.dto.RentedMovieStatisticsDto;

@RestController
public class Controller
{

    @Autowired
    private Service service;

    @Autowired
    private RentedMovieStatisticConverter rentedMovieStatisticConverter;

    @Autowired
    private ClientGenreConverter clientGenreConverter;

    @RequestMapping(value = "/statistics/movies", method = RequestMethod.GET)
    public RentedMovieStatisticsDto getTop10RentedMovies()
    {
        return new RentedMovieStatisticsDto(rentedMovieStatisticConverter.toDtos(service.getTop10RentedMovies()));
    }

    @RequestMapping(value = "/statistics/genres", method = RequestMethod.GET)
    public ClientGenresDto getClientGenres()
    {
        return new ClientGenresDto(clientGenreConverter.toDtos(service.getClientGenres()));
    }
}
