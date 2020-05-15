package ro.sdi.lab.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class Entity<ID extends Serializable> implements Serializable
{

    @NotNull(message = "Invalid id")
    @Id
    protected ID id;

    public Entity(ID id)
    {
        setId(id);
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
