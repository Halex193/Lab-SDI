package ro.sdi.lab.client.view.commands.movie.utils;

import ro.sdi.lab.core.model.Sort;

public class SortingCriteria
{
    private final String field;
    private final String direction;

    public SortingCriteria(String field, String direction)
    {
        this.field = field;
        this.direction = direction;
    }

    public String getField()
    {
        return field;
    }

    public Sort.Direction getDirection() {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }
}
