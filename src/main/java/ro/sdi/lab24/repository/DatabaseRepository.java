package ro.sdi.lab24.repository;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ro.sdi.lab24.exception.ValidatorException;
import ro.sdi.lab24.model.Entity;
import ro.sdi.lab24.model.Sort;
import ro.sdi.lab24.model.copyadapters.CopyAdapter;
import ro.sdi.lab24.repository.tableadapters.TableAdapter;
import ro.sdi.lab24.sorting.SortingUtils;

public class DatabaseRepository<ID extends Serializable, T extends Entity<ID>>
        implements SortingRepository<ID, T>
{
    private TableAdapter<ID, T> tableAdapter;
    private CopyAdapter<T> copyAdapter;

    public DatabaseRepository(
            TableAdapter<ID, T> tableAdapter,
            CopyAdapter<T> copyAdapter
    )
    {
        this.tableAdapter = tableAdapter;
        this.copyAdapter = copyAdapter;
    }

    @Override
    public Optional<T> findOne(ID id)
    {
        Objects.requireNonNull(id, "Id must not be null");
        return tableAdapter.findById(id);
    }

    @Override
    public Iterable<T> findAll()
    {
        return getAll();
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException
    {
        Objects.requireNonNull(entity, "Entity must not be null");
        Optional<T> readEntity = tableAdapter.findById(entity.getId());
        if (readEntity.isEmpty())
        {
            tableAdapter.save(entity);
        }
        return readEntity;
    }

    @Override
    public Optional<T> delete(ID id)
    {
        Objects.requireNonNull(id, "Id must not be null");
        return tableAdapter.findById(id).map(
                readEntity ->
                {
                    tableAdapter.deleteById(id);
                    return readEntity;
                }
        );
    }

    @Override
    @Transactional
    public Optional<T> update(T entity) throws ValidatorException
    {
        Objects.requireNonNull(entity, "Entity must not be null");

        return tableAdapter.findById(entity.getId()).map(
                readEntity ->
                {
                    tableAdapter.findById(entity.getId())
                                .ifPresent(e -> copyAdapter.copy(entity, e));
                    return entity;
                }
        );
    }

    private List<T> getAll()
    {
        return tableAdapter.findAll();
    }

    @Override
    public Iterable<T> findAll(Sort sort)
    {
        return SortingUtils.sort(getAll(), sort);
    }
}
