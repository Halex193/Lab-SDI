package ro.sdi.lab.core.repository.custom;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Rental;

public class CustomRepositoryNative extends CustomRepository
{
    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public Optional<Client> getClientAndMovies(int clientId)
    {
        HibernateEntityManager hibernateEntityManager = entityManager.unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query<Client> query = session.createSQLQuery(
                "select distinct {c.*},{r.*},{m.*} " +
                        "from client c " +
                        "left join rental r on c.id=r.client " +
                        "left join movie m on m.id=r.movie " +
                        "where c.id = ?1")
                                                   .addEntity("c", Client.class)
                                                   .addJoin("r", "c.clientRentals")
                                                   .addJoin("m", "r.movie")
                                                   .addEntity("c", Client.class)
                                                   .setParameter(1, clientId)
                                                   .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Client> clients = query.getResultList();
        if (clients.isEmpty())
            return Optional.empty();
        return Optional.of(clients.get(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Client> getClientsWithMovies()
    {
        HibernateEntityManager hibernateEntityManager = entityManager.unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query<Client> query = session.createSQLQuery(
                "select distinct {c.*},{r.*},{m.*} " +
                        "from client c " +
                        "left join rental r on c.id=r.client " +
                        "left join movie m on m.id=r.movie ")
                                                   .addEntity("c", Client.class)
                                                   .addJoin("r", "c.clientRentals")
                                                   .addJoin("m", "r.movie")
                                                   .addEntity("c", Client.class)
                                                   .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Rental> getAllRentals()
    {
        HibernateEntityManager hibernateEntityManager = entityManager.unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query<Rental> query = session.createSQLQuery(
                "select distinct {r.*},{c.*},{m.*} " +
                        "from rental r " +
                        "left join client c on c.id=r.client " +
                        "left join movie m on m.id=r.movie ")
                                                   .addEntity("r", Rental.class)
                                                   .addJoin("c", "r.client")
                                                   .addJoin("m", "r.movie")
                                                   .addEntity("r", Rental.class)
                                                   .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Rental> getRentalsWithMovies()
    {
        HibernateEntityManager hibernateEntityManager = entityManager.unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query<Rental> query = session.createSQLQuery(
                "select distinct {r.*},{m.*} " +
                        "from rental r " +
                        "left join movie m on m.id=r.movie ")
                                                   .addEntity("r", Rental.class)
                                                   .addJoin("m", "r.movie")
                                                   .addEntity("r", Rental.class)
                                                   .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return query.getResultList();
    }
}
