package ro.sdi.lab.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import ro.sdi.lab.core.model.Entity;
import ro.sdi.lab.core.model.Rental;

public interface Repository<ID extends Serializable, T extends Entity<ID>>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>
{

}
