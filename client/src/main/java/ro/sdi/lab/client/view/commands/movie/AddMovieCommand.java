package ro.sdi.lab.client.view.commands.movie;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.core.exception.ProgramException;

@Command(description = "Adds a movie", name = "add")
public class AddMovieCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie id")
    int id;

    @Parameters(index = "1", description = "Movie name")
    String name;

    @Parameters(index = "2", description = "Movie genre")
    String genre;

    @Parameters(index = "3", description = "Movie rating")
    int rating;

    @Override
    public void run()
    {
        try
        {
            Console.movieController.addMovie(id, name, genre, rating);
            System.out.println("Movie added!");
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
