package ro.sdi.lab.client.view.commands.movie;

import picocli.CommandLine.Command;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.core.exception.ProgramException;

import static picocli.CommandLine.Option;
import static picocli.CommandLine.Parameters;

@Command(description = "Update movie", name = "update")
public class UpdateMovieCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie id")
    int id;

    @Option(names = {"-n", "--name"}, description = "Movie name")
    String name = null;

    @Option(names = {"-g", "--genre"}, description = "Movie genre")
    String genre = null;

    @Option(names = {"-r", "--rating"}, description = "Movie rating")
    Integer rating = null;

    @Override
    public void run()
    {
        try
        {
            Console.movieController.updateMovie(id, name, genre, rating);
            System.out.println("Movie updated!");
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
