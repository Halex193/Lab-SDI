package ro.sdi.lab.core.repository.custom;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Rental;

public class CustomRepositoryJPQL extends CustomRepository
{
    @Override
    @SuppressWarnings("unchecked")
    public Optional<Client> getClientAndMovies(int clientId)
    {
        Query query = entityManager.createQuery(
                "select distinct c from Client c " +
                        "left join fetch c.clientRentals r " +
                        "left join fetch r.movie " +
                        "where c.id = :clientId"
        );
        query.setParameter("clientId", clientId);
        List<Client> clients = query.getResultList();

        if (clients.isEmpty())
            return Optional.empty();
        return Optional.of(clients.get(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Client> getClientsWithMovies()
    {
        Query query = entityManager.createQuery(
                "select distinct c from Client c " +
                        "left join fetch c.clientRentals r " +
                        "left join fetch r.movie "
        );
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Rental> getAllRentals()
    {
        Query query = entityManager.createQuery(
                "select distinct r from Rental r " +
                        "left join fetch r.client " +
                        "left join fetch r.movie "
        );
        return (List<Rental>) query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Rental> getRentalsWithMovies()
    {
        Query query = entityManager.createQuery(
                "select distinct r from Rental r " +
                        "left join fetch r.movie "
        );
        return query.getResultList();
    }
}
