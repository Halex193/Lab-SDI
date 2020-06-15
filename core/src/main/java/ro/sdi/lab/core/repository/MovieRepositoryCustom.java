package ro.sdi.lab.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.model.dto.ClientGenre;
import ro.sdi.lab.core.model.dto.RentedMovieStatistic;

public interface MovieRepositoryCustom
{
    List<Rental> getAllRentals();

    List<RentedMovieStatistic> getTop10RentedMovies();
}
