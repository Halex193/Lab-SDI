package ro.sdi.lab.core.model.dto;

import java.io.Serializable;

public class RentedMovieStatistic implements Comparable<RentedMovieStatistic>, Serializable
{
    private final String movieName;
    private final Long numberOfRentals;

    public RentedMovieStatistic(String movieName, Long numberOfRentals)
    {
        this.movieName = movieName;
        this.numberOfRentals = numberOfRentals;
    }

    public RentedMovieStatistic()
    {
        this.movieName = "";
        this.numberOfRentals = 0L;
    }

    public String getMovieName() {
        return movieName;
    }

    public Long getNumberOfRentals() {
        return numberOfRentals;
    }

    @Override
    public String toString() {
        return "RentedMovieStatistic{" +
                "movieName='" + movieName + '\'' +
                ", numberOfRentals=" + numberOfRentals +
                '}';
    }

    @Override
    public int compareTo(RentedMovieStatistic other) {
        return numberOfRentals.compareTo(this.getNumberOfRentals());
    }
}
