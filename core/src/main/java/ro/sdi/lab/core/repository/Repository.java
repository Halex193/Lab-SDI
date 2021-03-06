package ro.sdi.lab.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import ro.sdi.lab.core.model.Entity;

@NoRepositoryBean
@Transactional
public interface Repository<ID extends Serializable, T extends Entity<ID>>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>
{

}
