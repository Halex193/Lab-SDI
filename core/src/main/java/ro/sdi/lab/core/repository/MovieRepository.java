package ro.sdi.lab.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import ro.sdi.lab.core.model.Movie;

public interface MovieRepository extends Repository<Integer, Movie>, MovieRepositoryCustom
{
    @Query("SELECT DISTINCT movie FROM Movie movie")
    @EntityGraph(value = "movieRentals", type = EntityGraph.EntityGraphType.LOAD)
    List<Movie> findMoviesWithRentals();

    @Query("SELECT DISTINCT movie FROM Movie movie")
    @EntityGraph(value = "movieRentalsWithClients", type = EntityGraph.EntityGraphType.LOAD)
    List<Movie> findMoviesWithClients();
}
