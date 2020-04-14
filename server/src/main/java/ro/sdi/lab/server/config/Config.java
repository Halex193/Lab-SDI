package ro.sdi.lab.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import ro.sdi.lab.common.controller.ClientController;
import ro.sdi.lab.common.controller.Controller;
import ro.sdi.lab.common.controller.MovieController;
import ro.sdi.lab.common.controller.RentalController;
import ro.sdi.lab.common.model.Client;
import ro.sdi.lab.common.model.Movie;
import ro.sdi.lab.common.model.Rental;
import ro.sdi.lab.common.model.copyadapters.ClientCopyAdapter;
import ro.sdi.lab.common.model.copyadapters.CopyAdapter;
import ro.sdi.lab.common.model.copyadapters.MovieCopyAdapter;
import ro.sdi.lab.common.model.copyadapters.RentalCopyAdapter;
import ro.sdi.lab.common.networking.ServerInformation;
import ro.sdi.lab.server.controller.ClientControllerImpl;
import ro.sdi.lab.server.controller.ControllerImpl;
import ro.sdi.lab.server.controller.MovieControllerImpl;
import ro.sdi.lab.server.repository.DatabaseRepository;
import ro.sdi.lab.server.repository.Repository;
import ro.sdi.lab.server.repository.tableadapters.ClientTableAdapter;
import ro.sdi.lab.server.repository.tableadapters.MovieTableAdapter;
import ro.sdi.lab.server.repository.tableadapters.RentalTableAdapter;
import ro.sdi.lab.server.validation.ClientValidator;
import ro.sdi.lab.server.validation.MovieValidator;
import ro.sdi.lab.server.validation.RentalValidator;

@Configuration
@ComponentScan({"ro.sdi.lab.server.controller", "ro.sdi.lab.server.repository"})
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
    RmiServiceExporter controllerExporter(ControllerImpl controller)
    {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("Controller");
        rmiServiceExporter.setServiceInterface(Controller.class);
        rmiServiceExporter.setService(controller);
        rmiServiceExporter.setRegistryPort(ServerInformation.PORT);
        return rmiServiceExporter;
    }

    @Bean
    RmiServiceExporter clientExporter(ClientControllerImpl clientController)
    {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("ClientController");
        rmiServiceExporter.setServiceInterface(ClientController.class);
        rmiServiceExporter.setService(clientController);
        rmiServiceExporter.setRegistryPort(ServerInformation.PORT);
        return rmiServiceExporter;
    }

    @Bean
    RmiServiceExporter movieExporter(MovieControllerImpl movieController)
    {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("MovieController");
        rmiServiceExporter.setServiceInterface(MovieController.class);
        rmiServiceExporter.setService(movieController);
        rmiServiceExporter.setRegistryPort(ServerInformation.PORT);
        return rmiServiceExporter;
    }

    @Bean
    RmiServiceExporter rentalExporter(RentalController rentalController)
    {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("RentalController");
        rmiServiceExporter.setServiceInterface(RentalController.class);
        rmiServiceExporter.setService(rentalController);
        rmiServiceExporter.setRegistryPort(ServerInformation.PORT);
        return rmiServiceExporter;
    }
}
