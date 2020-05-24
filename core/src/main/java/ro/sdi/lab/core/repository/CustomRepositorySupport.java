package ro.sdi.lab.core.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.Getter;

@Getter
public abstract class CustomRepositorySupport
{
    @PersistenceContext
    private EntityManager entityManager;
}
