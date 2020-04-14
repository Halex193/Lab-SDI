package ro.sdi.lab24.sorting;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javafx.util.Pair;
import ro.sdi.lab24.exception.SortingException;
import ro.sdi.lab24.model.Sort;

public class SortingUtils
{
    private SortingUtils()
    {
    }

    public static <T> List<T> sort(List<T> entities, Sort sort)
    {
        if (entities.isEmpty())
        {
            return Collections.emptyList();
        }
        List<Pair<Sort.Direction, String>> sortingFields = new LinkedList<>(sort.sortingFields);
        Collections.reverse(sortingFields);

        sortingFields.forEach(
                sortingField ->
                {
                    Sort.Direction direction = sortingField.getKey();
                    String name = sortingField.getValue();

                    Class<?> entityClass = entities.get(0).getClass();
                    try
                    {
                        Field field = getField(entityClass, name);
                        field.setAccessible(true);
                        entities.sort(
                                (entity1, entity2) ->
                                {
                                    try
                                    {
                                        Object value1 = field.get(entity1);
                                        Object value2 = field.get(entity2);
                                        if (value1 instanceof Comparable)
                                        {
                                            int result = ((Comparable) value1).compareTo(value2);
                                            if (direction == Sort.Direction.DESC)
                                                return -result;
                                            return result;
                                        }
                                        throw new SortingException(
                                                "Sorting not implemented for type "
                                                        + field.getType().getSimpleName()
                                        );
                                    }
                                    catch (ClassCastException e)
                                    {
                                        throw new SortingException(
                                                "Sorting not implemented for type "
                                                        + field.getType().getSimpleName()
                                        );
                                    }
                                    catch (IllegalAccessException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    return 0;
                                });
                    }
                    catch (NoSuchFieldException e)
                    {
                        throw new SortingException(String.format(
                                "Field %s not present in class %s",
                                name,
                                entityClass.getSimpleName()
                        ));
                    }
                }
        );
        return entities;
    }

    /**
     * Returns the first {@link Field} in the hierarchy for the specified name
     */
    private static Field getField(Class<?> clazz, String name) throws NoSuchFieldException
    {
        Field field = null;
        while (clazz != null && field == null)
        {
            try
            {
                field = clazz.getDeclaredField(name);
            }
            catch (Exception ignored)
            {
            }
            clazz = clazz.getSuperclass();
        }
        return Optional.ofNullable(field).orElseThrow(NoSuchFieldException::new);
    }
}
