package ro.sdi.lab.common.model;

import ro.sdi.lab.common.utils.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Sort implements Serializable {
    public List<Pair<Direction, String>> sortingFields = new ArrayList<>();

    public Sort(String field) {
        addSorting(Direction.ASC, field);
    }

    public Sort(Direction direction, String... fields) {
        addSorting(direction, fields);
    }

    public Sort and(Sort sort)
    {
        sort.sortingFields.forEach(pair ->
                                   {
                                       addSorting(pair.getKey(), pair.getValue());
                                   });
        return this;
    }

    private void addSorting(Direction direction, String... fields)
    {
        sortingFields.addAll(
                Arrays.stream(fields)
                      .map(field -> new Pair<>(direction, field))
                      .collect(Collectors.toList())
        );
    }

    public enum Direction
    {
        ASC,
        DESC
    }
}
