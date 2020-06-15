package ro.sdi.lab.core.repository;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.model.dto.RentedMovieStatistic;
import ro.sdi.lab.core.repository.custom.CustomRepository;

@Component
public class MovieRepositoryImpl implements MovieRepositoryCustom
{
    CustomRepository customRepository;

    public MovieRepositoryImpl(CustomRepository customRepository)
    {
        this.customRepository = customRepository;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Rental> getAllRentals()
    {
        return customRepository.getAllRentals();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RentedMovieStatistic> getTop10RentedMovies()
    {
        List<Rental> rentals = customRepository.getRentalsWithMovies();

        return generateTop10RentedMovies(rentals);
    }

    List<RentedMovieStatistic> generateTop10RentedMovies(List<Rental> rentals)
    {
        return rentals
                .stream()
                .map(rental -> rental.getMovie().getName())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(entry -> new RentedMovieStatistic(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingLong(RentedMovieStatistic::getNumberOfRentals)
                                  .reversed()
                )
                .limit(10)
                .collect(Collectors.toUnmodifiableList());
    }
}
