package ro.sdi.lab.core.repository;

import java.io.Serializable;

import ro.sdi.lab.core.model.Entity;
import ro.sdi.lab.core.model.Sort;

public interface SortingRepository<ID extends Serializable, T extends Entity<ID>>
        extends Repository<ID, T>
{
    Iterable<T> findAll(Sort sort);
}
