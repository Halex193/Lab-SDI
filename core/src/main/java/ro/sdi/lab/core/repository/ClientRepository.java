package ro.sdi.lab.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.dto.ClientGenre;

public interface ClientRepository extends Repository<Integer, Client>, ClientRepositoryCustom
{
    @EntityGraph(value = "clientRentals", type = EntityGraph.EntityGraphType.LOAD)
    List<Client> findClientsWithRentals();

    @EntityGraph(value = "clientRentalsWithMovies", type = EntityGraph.EntityGraphType.LOAD)
    List<Client> findClientsWithMovies();
}
