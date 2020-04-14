package ro.sdi.lab.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sdi.lab.common.controller.MovieController;
import ro.sdi.lab.common.model.Movie;
import ro.sdi.lab.common.model.Sort;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class MovieControllerImpl implements FutureMovieController {
    public static final Logger log = LoggerFactory.getLogger(MovieControllerImpl.class);

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private MovieController movieController;

    @Override
    public Future<Void> addMovie(int id, String name, String genre, int rating) {
        Callable<Void> callable = () -> {
            log.trace("Sending request: add movie id={}, name={}, genre={}, rating={}", id, name, genre, rating);
            movieController.addMovie(id, name, genre, rating);
            log.trace("Received response: added movie id={}, name={}, genre={}, rating={}", id, name, genre, rating);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> deleteMovie(int id) {
        Callable<Void> callable = () -> {
            log.trace("Sending request: delete movie id={}", id);
            movieController.deleteMovie(id);
            log.trace("Received response: deleted movie id={}", id);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Movie>> getMovies() {
        Callable<Iterable<Movie>> callable = () -> {
            log.trace("Sending request: get all movies");
            Iterable<Movie> movies = movieController.getMovies();
            log.trace("Received response: get all movies");
            return movies;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> updateMovie(int id, String name, String genre, Integer rating) {
        Callable<Void> callable = () -> {
            log.trace("Sending request: update movie id={}, name={}, genre={}, rating={}", id, name, genre, rating);
            movieController.updateMovie(id, name, genre, rating);
            log.trace("Received response: updated movie id={}, name={}, genre={}, rating={}", id, name, genre, rating);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Movie>> filterMoviesByGenre(String genre) {
        Callable<Iterable<Movie>> callable = () -> {
            log.trace("Sending request: filter movies by genre={}", genre);
            Iterable<Movie> movies = movieController.filterMoviesByGenre(genre);
            log.trace("Received response: filtered movies by genre={}", genre);
            return movies;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Movie>> sortMovies(Sort criteria) {
        Callable<Iterable<Movie>> callable = () -> {
            log.trace("Sending request: sort movies by criteria={}", criteria);
            Iterable<Movie> movies = movieController.sortMovies(criteria);
            log.trace("Received response: sorted movies by criteria={}", criteria);
            return movies;
        };
        return executorService.submit(callable);
    }
}
