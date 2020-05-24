package ro.sdi.lab.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "movieRentals",
                attributeNodes = @NamedAttributeNode(value = "movieRentals")
        ),


        @NamedEntityGraph(
                name = "movieRentalsWithClients",
                attributeNodes = @NamedAttributeNode(
                        value = "movieRentals",
                        subgraph = "rentalClient"
                ),
                subgraphs = @NamedSubgraph(
                        name = "rentalClient",
                        attributeNodes = @NamedAttributeNode(value = "client")
                )
        )
})
@javax.persistence.Entity
public class Movie extends Entity<Integer> implements Serializable
{
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Invalid movie name")
    private String name;
    @NotNull
    private String genre;
    @NotNull
    @Min(0)
    @Max(100)
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

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Rental> movieRentals = new HashSet<>();

    public Set<Client> getClients()
    {
        return movieRentals.stream()
                            .map(Rental::getClient)
                            .collect(Collectors.toUnmodifiableSet());
    }

    public void rentMovie(Client client, LocalDateTime time)
    {
        movieRentals.add(new Rental(this, client, time));
    }
}
