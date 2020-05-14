package ro.sdi.lab.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import ro.sdi.lab.core.service.Service;
import ro.sdi.lab.web.converter.ClientGenreConverter;
import ro.sdi.lab.web.converter.RentedMovieStatisticConverter;
import ro.sdi.lab.web.dto.ClientGenreDto;
import ro.sdi.lab.web.dto.RentedMovieStatisticDto;

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
    public List<RentedMovieStatisticDto> getTop10RentedMovies()
    {
        return rentedMovieStatisticConverter.toDtos(service.getTop10RentedMovies());
    }

    @RequestMapping(value = "/statistics/genres", method = RequestMethod.GET)
    public List<ClientGenreDto> getClientGenres()
    {
        return clientGenreConverter.toDtos(service.getClientGenres());
    }
}
