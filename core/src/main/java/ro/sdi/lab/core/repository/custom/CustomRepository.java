package ro.sdi.lab.core.repository.custom;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Rental;

public abstract class CustomRepository
{
    @PersistenceContext
    protected EntityManager entityManager;

    public abstract Optional<Client> getClientAndMovies(int clientId);

    public abstract List<Client> getClientsWithMovies();

    public abstract List<Rental> getAllRentals();

    public abstract List<Rental> getRentalsWithMovies();
}
