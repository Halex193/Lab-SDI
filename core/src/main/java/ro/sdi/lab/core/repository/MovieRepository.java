package ro.sdi.lab.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.model.dto.RentedMovieStatistic;

public interface MovieRepository extends Repository<Integer, Movie>
{
    @EntityGraph(value = "movieRentals", type = EntityGraph.EntityGraphType.LOAD)
    List<Rental> getAllRentals();

    @EntityGraph(value = "movieRentals", type = EntityGraph.EntityGraphType.LOAD)
    List<RentedMovieStatistic> getTop10RentedMovies();
}
