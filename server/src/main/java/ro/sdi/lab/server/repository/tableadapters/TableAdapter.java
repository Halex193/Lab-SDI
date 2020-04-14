package ro.sdi.lab.server.repository.tableadapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

import ro.sdi.lab.common.model.Entity;

@NoRepositoryBean
public interface TableAdapter<ID extends Serializable, T extends Entity<ID>>
        extends JpaRepository<T, ID>
{

}
