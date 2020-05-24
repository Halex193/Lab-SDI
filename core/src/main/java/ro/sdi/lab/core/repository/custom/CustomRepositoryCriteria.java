package ro.sdi.lab.core.repository.custom;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Client_;
import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.model.Rental_;

public class CustomRepositoryCriteria extends CustomRepository
{
    @Override
    public Optional<Client> getClientAndMovies(int clientId)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = criteriaBuilder.createQuery(Client.class);
        query.distinct(Boolean.TRUE);
        Root<Client> root = query.from(Client.class);
        query.select(root);
        Fetch<Client, Rental> clientRentalsFetch = root.fetch(Client_.clientRentals, JoinType.LEFT);
        clientRentalsFetch.fetch(Rental_.movie, JoinType.LEFT);
        ParameterExpression<Integer> clientIdParameter = criteriaBuilder.parameter(Integer.class);
        query.where(criteriaBuilder.equal(root.get("id"), clientIdParameter));

        TypedQuery<Client> resultQuery = entityManager.createQuery(query);
        resultQuery.setParameter(clientIdParameter, clientId);
        List<Client> clients = resultQuery.getResultList();

        if (clients.isEmpty())
            return Optional.empty();
        return Optional.of(clients.get(0));
    }

    @Override
    public List<Client> getClientsWithMovies()
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = criteriaBuilder.createQuery(Client.class);
        query.distinct(Boolean.TRUE);
        Root<Client> root = query.from(Client.class);
        query.select(root);
        Fetch<Client, Rental> clientRentalsFetch = root.fetch(Client_.clientRentals, JoinType.LEFT);
        clientRentalsFetch.fetch(Rental_.movie, JoinType.LEFT);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Rental> getAllRentals()
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Rental> query = criteriaBuilder.createQuery(Rental.class);
        query.distinct(Boolean.TRUE);
        Root<Rental> root = query.from(Rental.class);
        query.select(root);
        root.fetch(Rental_.client, JoinType.LEFT);
        root.fetch(Rental_.movie, JoinType.LEFT);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Rental> getRentalsWithMovies()
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Rental> query = criteriaBuilder.createQuery(Rental.class);
        query.distinct(Boolean.TRUE);
        Root<Rental> root = query.from(Rental.class);
        query.select(root);
        root.fetch(Rental_.movie, JoinType.LEFT);

        return entityManager.createQuery(query).getResultList();
    }
}
