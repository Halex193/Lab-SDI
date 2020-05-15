package ro.sdi.lab.core.model.copyadapters;


import org.springframework.stereotype.Component;

import ro.sdi.lab.core.model.Client;

@Component
public class ClientCopyAdapter implements CopyAdapter<Client>
{
    @Override
    public void copy(Client source, Client destination)
    {
        destination.setId(source.getId());
        destination.setName(source.getName());
    }
}
