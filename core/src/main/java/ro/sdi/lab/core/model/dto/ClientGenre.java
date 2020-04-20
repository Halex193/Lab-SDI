package ro.sdi.lab.core.model.dto;

import java.io.Serializable;

import ro.sdi.lab.core.model.Client;


public class ClientGenre implements Serializable
{
    private final Client client;
    private final String genre;

    public ClientGenre(Client client, String genre)
    {
        this.client = client;
        this.genre = genre;
    }

    public Client getClient()
    {
        return client;
    }

    public String getGenre()
    {
        return genre;
    }
}