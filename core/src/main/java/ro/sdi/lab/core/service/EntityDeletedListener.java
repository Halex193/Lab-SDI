package ro.sdi.lab.core.service;

@FunctionalInterface
public interface EntityDeletedListener<T>
{
    void onEntityDeleted(T entity);
}
