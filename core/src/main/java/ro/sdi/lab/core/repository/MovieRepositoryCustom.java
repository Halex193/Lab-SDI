package ro.sdi.lab.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.model.dto.RentedMovieStatistic;

public interface MovieRepositoryCustom
{
    List<Rental> getAllRentals();

    List<RentedMovieStatistic> getTop10RentedMovies();
}
