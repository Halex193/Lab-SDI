package ro.sdi.lab.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import ro.sdi.lab.core.model.Client;

public interface ClientRepository extends Repository<Integer, Client>, ClientRepositoryCustom
{
    @Query("SELECT DISTINCT client FROM Client client")
    @EntityGraph(value = "clientRentals", type = EntityGraph.EntityGraphType.LOAD)
    List<Client> findClientsWithRentals();

    @Query("SELECT DISTINCT client FROM Client client")
    @EntityGraph(value = "clientRentalsWithMovies", type = EntityGraph.EntityGraphType.LOAD)
    List<Client> findClientsWithMovies();
}
