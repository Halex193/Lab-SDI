package ro.sdi.lab.web.converter;

import org.springframework.stereotype.Component;

import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.web.dto.ClientDto;

@Component
public class ClientConverter implements Converter<Client, ClientDto>
{

    @Override
    public Client toModel(ClientDto clientDto)
    {
        return new Client(clientDto.getId(), clientDto.getName());
    }

    @Override
    public ClientDto toDto(Client client)
    {
        return ClientDto.builder()
                        .id(client.getId())
                        .name(client.getName())
                        .build();
    }
}
