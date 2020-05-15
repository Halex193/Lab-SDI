package ro.sdi.lab.core.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

import ro.sdi.lab.core.model.Entity;
import ro.sdi.lab.core.model.Sort;

public interface SortingRepository<ID extends Serializable, T extends Entity<ID>>
        extends Repository<ID, T>
{
    Iterable<T> findAll(Sort sort);
}
