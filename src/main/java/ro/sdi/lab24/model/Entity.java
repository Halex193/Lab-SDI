package ro.sdi.lab24.model;

import java.util.Objects;

public class Entity<ID>
{
    protected ID id;

    public Entity(ID id)
    {
        this.id = id;
    }

    public ID getId()
    {
        return id;
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
