package ro.sdi.lab24.model.copyadapters;


import ro.sdi.lab24.model.Client;

public class ClientCopyAdapter implements CopyAdapter<Client>
{
    @Override
    public void copy(Client source, Client destination)
    {
        destination.setId(source.getId());
        destination.setName(source.getName());
    }
}
