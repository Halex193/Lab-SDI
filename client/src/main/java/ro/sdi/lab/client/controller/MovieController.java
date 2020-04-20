package ro.sdi.lab.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import ro.sdi.lab.core.exception.AlreadyExistingElementException;
import ro.sdi.lab.core.exception.ElementNotFoundException;
import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.model.Sort;
import ro.sdi.lab.web.converter.MovieConverter;
import ro.sdi.lab.web.dto.ClientsDto;
import ro.sdi.lab.web.dto.MoviesDto;

@Service
public class MovieController
{
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MovieConverter movieConverter;

    public static final String URL = "http://localhost:8080/api/movies";
    public static final Logger log = LoggerFactory.getLogger(MovieController.class);

    /**
     * This function adds a movie to the repository
     *
     * @param id:   the ID of the movie
     * @param name: the name of the movie
     * @throws AlreadyExistingElementException if the movie (the ID) is already there
     */
    public void addMovie(int id, String name, String genre, int rating)
    {
        Movie movie = new Movie(id, name, genre, rating);
        try
        {
            restTemplate.postForEntity(
                    URL,
                    movie,
                    Object.class
            );
            log.trace("Movie {} added", movie);
        }
        catch (RestClientException e)
        {
            log.trace("Movie {} already exists", movie);
            throw new AlreadyExistingElementException("Movie already exists!");
        }
    }

    /**
     * This function removes a movie from the repository based on their ID
     *
     * @param id: the ID of the movie
     * @throws ElementNotFoundException if the movie isn't found in the repository based on their ID
     */
    public void deleteMovie(int id)
    {
        try
        {
            restTemplate.delete(URL + "/" + id);
            log.trace("Movie with id {} deleted", id);
        }
        catch (RestClientException e)
        {
            log.trace("Movie with id {} was not deleted", id);
            throw new ElementNotFoundException("Movie does not exist!");
        }
    }

    /**
     * This function returns an iterable collection of the current state of the movies in the repository
     *
     * @return all: an iterable collection of movies
     */
    public Iterable<Movie> getMovies()
    {
        MoviesDto moviesDto = restTemplate.getForObject(URL, MoviesDto.class);
        assert moviesDto != null;
        List<Movie> movies = moviesDto.getMovies()
                                         .stream()
                                         .map(movieConverter::toModel)
                                         .collect(Collectors.toList());
        log.trace("Fetched movies {}", movies);
        return movies;
    }

    /**
     * This function updated a movie based on their ID with a new name
     *
     * @param id     : the movie's ID
     * @param name   : the new name of the movie
     * @param genre  : the genre of the movie
     * @param rating : the rating of the movie
     * @throws ElementNotFoundException if the movie isn't found in the repository based on their ID
     */
    public void updateMovie(
            int id,
            String name,
            String genre,
            Integer rating
    )
    {
        Movie movie = new Movie(id, name, genre, rating);
        try
        {
            restTemplate.put(URL + "/" + id, movie);
            log.trace("Updated movie {}", movie);
        }
        catch (RestClientException e)
        {
            log.trace("Movie {} was not updated", movie);
            throw new ElementNotFoundException("Movie not found!");
        }
    }

    public Iterable<Movie> filterMoviesByGenre(String genre)
    {
        MoviesDto moviesDto = restTemplate.getForObject(URL + "/filter/" + genre, MoviesDto.class);
        assert moviesDto != null;
        List<Movie> movies = moviesDto.getMovies()
                                         .stream()
                                         .map(movieConverter::toModel)
                                         .collect(Collectors.toList());
        log.trace("Filtered movies by genre {}: {}", genre, movies);
        return movies;
    }

    public Iterable<Movie> sortMovies(Sort criteria)
    {
        MoviesDto moviesDto = restTemplate.postForObject(URL + "/sort", criteria, MoviesDto.class);
        assert moviesDto != null;
        List<Movie> movies = moviesDto.getMovies()
                                      .stream()
                                      .map(movieConverter::toModel)
                                      .collect(Collectors.toList());
        log.trace("Sorted movies by criteria {}: {}", criteria, movies);
        return movies;
    }
}
