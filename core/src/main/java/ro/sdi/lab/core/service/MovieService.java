package ro.sdi.lab.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ro.sdi.lab.core.exception.AlreadyExistingElementException;
import ro.sdi.lab.core.exception.ElementNotFoundException;
import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.repository.MovieRepository;
import ro.sdi.lab.core.repository.Repository;
import ro.sdi.lab.core.validation.Validator;

@Service
public class MovieService
{
    public static final Logger log = LoggerFactory.getLogger(MovieService.class);
    MovieRepository movieRepository;
    Validator<Movie> movieValidator;

    public MovieService(
            MovieRepository movieRepository,
            Validator<Movie> movieValidator
    )
    {
        this.movieRepository = movieRepository;
        this.movieValidator = movieValidator;
    }

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
        movieValidator.validate(movie);
        log.trace("Adding movie {}", movie);

        movieRepository.findById(id).ifPresent(
                (item) ->
                {
                    throw new AlreadyExistingElementException(String.format(
                            "Movie %d already exists",
                            id
                    ));
                });
        movieRepository.save(movie);
    }

    /**
     * This function removes a movie from the repository based on their ID
     *
     * @param id: the ID of the movie
     * @throws ElementNotFoundException if the movie isn't found in the repository based on their ID
     */
    public void deleteMovie(int id)
    {
        log.trace("Removing movie with id {}", id);
        if (movieRepository.findById(id).isEmpty())
        {
            throw new ElementNotFoundException(String.format(
                    "Movie %d does not exist",
                    id
            ));
        }
        movieRepository.deleteById(id);
    }

    /**
     * This function returns an iterable collection of the current state of the movies in the repository
     *
     * @return all: an iterable collection of movies
     */
    public List<Movie> getMovies()
    {
        log.trace("Retrieving all movies");
        return movieRepository.findAll();
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
    @Transactional
    public void updateMovie(
            int id,
            String name,
            String genre,
            Integer rating
    )
    {
        Movie movie = new Movie(id, name, genre, rating);
        movieValidator.validate(movie);
        log.trace("Updating movie {}", movie);

        Movie oldMovie = movieRepository
                .findById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        String.format(
                                "Movie %d does not exist",
                                id
                        )));
        oldMovie.setId(movie.getId());
        oldMovie.setName(movie.getName());
        oldMovie.setGenre(movie.getGenre());
        oldMovie.setRating(movie.getRating());
        movieRepository.save(oldMovie);
    }

    public Iterable<Movie> filterMoviesByGenre(String genre)
    {
        log.trace("Filtering movies by the genre {}", genre);
        String regex = ".*" + genre + ".*";
        return movieRepository.findAll().stream()
                            .filter(movie -> movie.getGenre().matches(regex))
                            .collect(Collectors.toUnmodifiableList());
    }

    public Optional<Movie> findOne(int movieId)
    {
        return movieRepository.findById(movieId);
    }
}
