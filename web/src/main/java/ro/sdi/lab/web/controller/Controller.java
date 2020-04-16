package ro.sdi.lab.web.controller;

import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.model.dto.ClientGenre;
import ro.sdi.lab.core.model.dto.RentedMovieStatistic;

public class Controller
{

    public Controller()
    {

    }

    public Iterable<RentedMovieStatistic> getTop10RentedMovies()
    {
        return null;
    }

    /**
     * Retrieves the most rented genre for each client, or the empty string if the client did not rent any movies
     */
    public Iterable<ClientGenre> getClientGenres()
    {
        return null;
    }

    private String getMovieGenre(Rental rental)
    {
        return null;
    }
}
