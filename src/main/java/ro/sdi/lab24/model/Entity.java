package ro.sdi.lab24.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Entity<ID extends Serializable> implements Serializable
{

    @Id
    protected ID id;

    public Entity(ID id)
    {
        this.id = id;
    }

    public ID getId()
    {
        return id;
    }

    public void setId(ID id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId());
    }
}
