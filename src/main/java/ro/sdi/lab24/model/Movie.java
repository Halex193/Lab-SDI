package ro.sdi.lab24.model;

import java.io.Serializable;

@javax.persistence.Entity
public class Movie extends Entity<Integer> implements Serializable
{
    private String name;
    private String genre;
    private int rating;

    public Movie()
    {
        super(0);
    }

    public Movie(int id, String name, String genre, int rating)
    {
        super(id);
        this.name = name;
        this.genre = genre;
        this.rating = rating;
    }

    public String getName()
    {
        return name;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return String.format("Movie[%d, %s, %s, %d]", id, name, genre, rating);
    }
}
