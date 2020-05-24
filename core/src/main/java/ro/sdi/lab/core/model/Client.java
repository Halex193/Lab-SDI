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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "clientRentals",
                attributeNodes = @NamedAttributeNode(value = "clientRentals")
        ),


        @NamedEntityGraph(
                name = "clientRentalsWithMovies",
                attributeNodes = @NamedAttributeNode(
                        value = "clientRentals",
                        subgraph = "rentalMovie"
                ),
                subgraphs = @NamedSubgraph(
                        name = "rentalMovie",
                        attributeNodes = @NamedAttributeNode(value = "movie")
                )
        )
})
@javax.persistence.Entity
public class Client extends Entity<Integer> implements Serializable
{
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid client name")
    private String name;

    public Client()
    {
        super(0);
    }

    public Client(int id, String name)
    {
        super(id);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return String.format("Client[%d, %s]", id, name);
    }

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Rental> clientRentals = new HashSet<>();

    public Set<Movie> getMovies()
    {
        return clientRentals.stream()
                            .map(Rental::getMovie)
                            .collect(Collectors.toUnmodifiableSet());
    }

    public void rentMovie(Movie movie, LocalDateTime time)
    {
        clientRentals.add(new Rental(movie, this, time));
    }

    public void deleteMovie(Movie movie)
    {
        clientRentals = clientRentals
                .stream()
                .filter(rental -> rental.getMovie() != movie)
                .collect(Collectors.toSet());
    }

    public void updateRentalTime(Movie movie, LocalDateTime dateTime)
    {
        clientRentals.stream()
                     .filter(rental -> rental.getMovie() == movie)
                     .forEach(rental -> rental.setTime(dateTime));
    }
}
