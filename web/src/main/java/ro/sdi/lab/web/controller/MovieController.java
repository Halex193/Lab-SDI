package ro.sdi.lab.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import ro.sdi.lab.core.exception.AlreadyExistingElementException;
import ro.sdi.lab.core.exception.ElementNotFoundException;
import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.service.MovieService;
import ro.sdi.lab.web.converter.MovieConverter;
import ro.sdi.lab.web.dto.MovieDto;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class MovieController
{

    public static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieConverter movieConverter;

    @RequestMapping(value = "/movies", method = GET)
    public List<MovieDto> getMovies()
    {
        Iterable<Movie> movies = movieService.getMovies();
        log.trace("Get movies: {}", movies);
        return movieConverter.toDtos(movies);
    }

    @RequestMapping(value = "/movies", method = POST)
    public ResponseEntity<?> addMovie(@RequestBody MovieDto movieDto)
    {
        Movie movie = movieConverter.toModel(movieDto);
        try
        {
            movieService.addMovie(
                    movie.getId(),
                    movie.getName(),
                    movie.getGenre(),
                    movie.getRating()
            );
        }
        catch (AlreadyExistingElementException e)
        {
            log.trace("Movie {} already exists", movie);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        log.trace("Movie {} added", movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/movies/{id}", method = DELETE)
    public ResponseEntity<?> deleteMovie(@PathVariable int id)
    {
        try
        {
            movieService.deleteMovie(id);
        }
        catch (ElementNotFoundException e)
        {
            log.trace("Movie with id {} could not be deleted", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.trace("Movie with id {} was deleted", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/movies/{id}", method = PUT)
    public ResponseEntity<?> updateMovie(@PathVariable int id, @RequestBody MovieDto movieDto)
    {
        Movie movie = movieConverter.toModel(movieDto);
        try
        {
            movieService.updateMovie(id, movie.getName(), movie.getGenre(), movie.getRating());
        }
        catch (ElementNotFoundException e)
        {
            log.trace("Movie with id {} could not be updated", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.trace("Movie with id {} was updated: {}", id, movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/movies/filter/{genre}", method = GET)
    public List<MovieDto> filterMoviesByGenre(@PathVariable String genre)
    {
        return movieConverter.toDtos(movieService.filterMoviesByGenre(genre));
    }

    @RequestMapping(value = "/movies/{page}", method = GET)
    public List<MovieDto> getMovies(
            @PathVariable int page,
            @RequestParam(defaultValue = "3") int pageSize,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "") String filter
    )
    {
        Sort sortObject = Sort.by(sort);
        if (order.equals("asc")) sortObject = sortObject.ascending();
        if (order.equals("desc")) sortObject = sortObject.descending();

        Iterable<Movie> movies = movieService.getMovies(filter, sortObject, page, pageSize);
        log.trace("Get movies page {} with filter {}, sort by {}, order {}: {}", page, filter, sort, order, movies);
        return movieConverter.toDtos(movies);
    }
}
