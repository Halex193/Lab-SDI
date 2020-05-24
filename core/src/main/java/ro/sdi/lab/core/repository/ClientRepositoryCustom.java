package ro.sdi.lab.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.dto.ClientGenre;

public interface ClientRepositoryCustom
{
    Optional<Client> getClientAndMovies(int clientId);

    List<ClientGenre> getClientGenres();
}
