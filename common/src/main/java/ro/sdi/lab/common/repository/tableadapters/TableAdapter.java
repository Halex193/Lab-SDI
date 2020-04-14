package ro.sdi.lab24.repository.tableadapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

import ro.sdi.lab24.model.Entity;

@NoRepositoryBean
public interface TableAdapter<ID extends Serializable, T extends Entity<ID>>
        extends JpaRepository<T, ID>
{

}
