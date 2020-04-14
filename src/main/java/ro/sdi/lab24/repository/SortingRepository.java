package ro.sdi.lab24.repository;

import java.io.Serializable;

import ro.sdi.lab24.model.Entity;
import ro.sdi.lab24.model.Sort;

public interface SortingRepository<ID extends Serializable, T extends Entity<ID>>
        extends Repository<ID, T>
{
    Iterable<T> findAll(Sort sort);
}
