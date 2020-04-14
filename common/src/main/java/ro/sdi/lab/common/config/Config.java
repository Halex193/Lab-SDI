package ro.sdi.lab24.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ro.sdi.lab24.model.Client;
import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.model.Rental;
import ro.sdi.lab24.model.copyadapters.ClientCopyAdapter;
import ro.sdi.lab24.model.copyadapters.CopyAdapter;
import ro.sdi.lab24.model.copyadapters.MovieCopyAdapter;
import ro.sdi.lab24.model.copyadapters.RentalCopyAdapter;
import ro.sdi.lab24.repository.DatabaseRepository;
import ro.sdi.lab24.repository.Repository;
import ro.sdi.lab24.repository.tableadapters.ClientTableAdapter;
import ro.sdi.lab24.repository.tableadapters.MovieTableAdapter;
import ro.sdi.lab24.repository.tableadapters.RentalTableAdapter;
import ro.sdi.lab24.validation.ClientValidator;
import ro.sdi.lab24.validation.MovieValidator;
import ro.sdi.lab24.validation.RentalValidator;

@Configuration
@ComponentScan({"ro.sdi.lab24.view", "ro.sdi.lab24.controller", "ro.sdi.lab24.repository"})
public class Config
{
    @Bean
    CopyAdapter<Client> clientCopyAdapter()
    {
        return new ClientCopyAdapter();
    }

    @Bean
    CopyAdapter<Movie> movieCopyAdapter()
    {
        return new MovieCopyAdapter();
    }

    @Bean
    CopyAdapter<Rental> rentalCopyAdapter()
    {
        return new RentalCopyAdapter();
    }


    @Bean
    Repository<Integer, Client> clientRepository(
            ClientTableAdapter clientTableAdapter,
            CopyAdapter<Client> clientCopyAdapter
    )
    {
        return new DatabaseRepository<>(clientTableAdapter, clientCopyAdapter);
    }

    @Bean
    Repository<Integer, Movie> movieRepository(
            MovieTableAdapter movieTableAdapter,
            CopyAdapter<Movie> movieCopyAdapter
    )
    {
        return new DatabaseRepository<>(movieTableAdapter, movieCopyAdapter);
    }

    @Bean
    Repository<Rental.RentalID, Rental> rentalRepository(
            RentalTableAdapter rentalTableAdapter,
            CopyAdapter<Rental> rentalCopyAdapter
    )
    {
        return new DatabaseRepository<>(rentalTableAdapter, rentalCopyAdapter);
    }

    @Bean
    RentalValidator rentalValidator()
    {
        return new RentalValidator();
    }

    @Bean
    MovieValidator movieValidator()
    {
        return new MovieValidator();
    }

    @Bean
    ClientValidator clientValidator()
    {
        return new ClientValidator();
    }

    @Bean
    ExecutorService executorService()
    {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    DateTimeFormatter dateFormatter()
    {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    }
}
