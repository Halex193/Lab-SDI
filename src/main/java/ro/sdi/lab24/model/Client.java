package ro.sdi.lab24.model;

import java.io.Serializable;

@javax.persistence.Entity
public class Client extends Entity<Integer> implements Serializable
{
    private String name;

    public Client()
    {
        super(0);
    }

    public Client(int id, String name)
    {
        super(id);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return String.format("Client[%d, %s]", id, name);
    }
}
