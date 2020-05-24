package ro.sdi.lab.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

import ro.sdi.lab.core.model.Movie;

public interface MovieRepository extends Repository<Integer, Movie>, MovieRepositoryCustom
{
    @EntityGraph(value = "movieRentals", type = EntityGraph.EntityGraphType.LOAD)
    List<Movie> findMoviesWithRentals();

    @EntityGraph(value = "movieRentalsWithClients", type = EntityGraph.EntityGraphType.LOAD)
    List<Movie> findMoviesWithClients();
}
