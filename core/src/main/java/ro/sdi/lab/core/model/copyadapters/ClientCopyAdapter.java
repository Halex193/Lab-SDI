package ro.sdi.lab.core.model.copyadapters;


import ro.sdi.lab.core.model.Client;

public class ClientCopyAdapter implements CopyAdapter<Client>
{
    @Override
    public void copy(Client source, Client destination)
    {
        destination.setId(source.getId());
        destination.setName(source.getName());
    }
}
