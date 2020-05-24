package ro.sdi.lab.core.repository;

import java.util.List;
import java.util.Optional;

import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.dto.ClientGenre;

public interface ClientRepository extends Repository<Integer, Client>
{
    Optional<Client> getClientAndMovies(int clientId);

    List<ClientGenre> getClientGenres();
}
