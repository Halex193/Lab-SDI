package ro.sdi.lab.server.repository;

import ro.sdi.lab.common.exception.ValidatorException;
import ro.sdi.lab.common.model.Entity;
import ro.sdi.lab.common.model.Sort;
import ro.sdi.lab.common.model.copyadapters.CopyAdapter;
import ro.sdi.lab.server.repository.tableadapters.TableAdapter;
import ro.sdi.lab.server.sorting.SortingUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public Optional<T> update(T entity) throws ValidatorException
    {
        Objects.requireNonNull(entity, "Entity must not be null");

        return tableAdapter.findById(entity.getId()).map(
                readEntity ->
                {
                    tableAdapter.findById(entity.getId())
                            .ifPresent(e -> {
                                tableAdapter.save(entity);
                            });
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
