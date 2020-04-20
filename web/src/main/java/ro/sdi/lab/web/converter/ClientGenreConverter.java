package ro.sdi.lab.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.sdi.lab.core.model.dto.ClientGenre;
import ro.sdi.lab.web.dto.ClientGenreDto;

@Component
public class ClientGenreConverter implements Converter<ClientGenre, ClientGenreDto>
{
    private final ClientConverter clientConverter;

    @Autowired
    public ClientGenreConverter(ClientConverter clientConverter)
    {
        this.clientConverter = clientConverter;
    }

    @Override
    public ClientGenre toModel(ClientGenreDto clientGenreDto)
    {
        return new ClientGenre(
                clientConverter.toModel(clientGenreDto.getClient()),
                clientGenreDto.getGenre()
        );
    }

    @Override
    public ClientGenreDto toDto(ClientGenre clientGenre)
    {
        return ClientGenreDto.builder()
                             .client(clientConverter.toDto(clientGenre.getClient()))
                             .genre(clientGenre.getGenre())
                             .build();
    }
}
