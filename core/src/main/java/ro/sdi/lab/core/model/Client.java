package ro.sdi.lab.core.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@javax.persistence.Entity
public class Client extends Entity<Integer> implements Serializable
{
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid client name")
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
