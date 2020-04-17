package ro.sdi.lab.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import ro.sdi.lab.core.exception.ElementNotFoundException;
import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.model.Sort;
import ro.sdi.lab.core.service.MovieService;
import ro.sdi.lab.web.converter.MovieConverter;
import ro.sdi.lab.web.dto.MovieDto;
import ro.sdi.lab.web.dto.MoviesDto;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MovieController
{

    public static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieConverter movieConverter;

    @RequestMapping(value = "/movies", method = GET)
    public MoviesDto getMovies()
    {
        Iterable<Movie> movies = movieService.getMovies();
        log.trace("Get movies: {}", movies);
        return new MoviesDto(movieConverter.toDtos(movies));
    }

    @RequestMapping(value = "/movies", method = POST)
    public ResponseEntity<?> addMovie(@RequestBody MovieDto movieDto)
    {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);//TODO
    }

    /**
     * This function removes a movie from the repository based on their ID
     *
     * @param id: the ID of the movie
     * @throws ElementNotFoundException if the movie isn't found in the repository based on their ID
     */
    public void deleteMovie(int id)
    {

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

    }

    public Iterable<Movie> filterMoviesByGenre(String genre)
    {
        return null;
    }

    public Optional<Movie> findOne(int movieId)
    {
        return null;
    }

    public Iterable<Movie> sortMovies(Sort criteria)
    {
        return null;
    }
}
