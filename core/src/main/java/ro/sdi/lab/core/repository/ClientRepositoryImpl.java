package ro.sdi.lab.core.repository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.model.dto.ClientGenre;
import ro.sdi.lab.core.repository.custom.CustomRepository;

@Component
public class ClientRepositoryImpl implements ClientRepositoryCustom
{
    CustomRepository customRepository;

    public ClientRepositoryImpl(CustomRepository customRepository)
    {
        this.customRepository = customRepository;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Client> getClientAndMovies(int clientId)
    {
        return customRepository.getClientAndMovies(clientId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ClientGenre> getClientGenres()
    {
        List<Client> clients = customRepository.getClientsWithMovies();
        return generateClientGenres(clients);
    }

    List<ClientGenre> generateClientGenres(List<Client> clients)
    {
        return clients
                .stream()
                .map(client -> new ClientGenre(
                             client,
                             client.getMovies()
                                   .stream()
                                   .map(Movie::getGenre)
                                   .collect(Collectors.groupingBy(
                                           Function.identity(),
                                           Collectors.counting()
                                   ))
                                   .entrySet()
                                   .stream()
                                   .max(Map.Entry.comparingByValue())
                                   .map(Map.Entry::getKey)
                                   .orElse("")
                     )
                )
                .collect(Collectors.toUnmodifiableList());
    }

}
