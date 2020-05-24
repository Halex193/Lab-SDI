package ro.sdi.lab.core.repository;

import java.util.List;

import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.model.dto.RentedMovieStatistic;

public interface MovieRepository extends Repository<Integer, Movie>
{
    List<Rental> getAllRentals();

    List<RentedMovieStatistic> getTop10RentedMovies();
}
