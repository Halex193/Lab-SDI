package ro.sdi.lab.common.model.copyadapters;

import ro.sdi.lab.common.model.Client;

public class ClientCopyAdapter implements CopyAdapter<Client>
{
    @Override
    public void copy(Client source, Client destination)
    {
        destination.setId(source.getId());
        destination.setName(source.getName());
    }
}
