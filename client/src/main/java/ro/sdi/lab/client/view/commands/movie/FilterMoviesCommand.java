package ro.sdi.lab.client.view.commands.movie;


import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.core.exception.ProgramException;
import ro.sdi.lab.core.model.Movie;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Command(description = "Filter movie by genre", name = "filter")
public class FilterMoviesCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie genre")
    String genre;

    @Override
    public void run()
    {
        try
        {
            Iterable<Movie> filteredMovies = Console.movieController.filterMoviesByGenre(genre);
            if (!filteredMovies.iterator().hasNext())
            {
                System.out.println("No movies found!");
            }
            filteredMovies.forEach(
                    movie -> System.out.printf(
                            "%d %s %s %d\n",
                            movie.getId(),
                            movie.getName(),
                            movie.getGenre(),
                            movie.getRating()
                    )
            );
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
