package ro.sdi.lab.server.repository;

import java.io.Serializable;

import ro.sdi.lab.common.model.Entity;
import ro.sdi.lab.common.model.Sort;

public interface SortingRepository<ID extends Serializable, T extends Entity<ID>>
        extends Repository<ID, T>
{
    Iterable<T> findAll(Sort sort);
}
