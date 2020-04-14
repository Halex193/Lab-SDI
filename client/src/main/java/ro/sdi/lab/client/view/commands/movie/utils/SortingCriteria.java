package ro.sdi.lab24.view.commands.movie.utils;

import ro.sdi.lab24.model.Sort;

public class SortingCriteria {
    private String field;
    private String direction;

    public SortingCriteria(String field, String direction) {
        this.field = field;
        this.direction = direction;
    }

    public String getField() {
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
