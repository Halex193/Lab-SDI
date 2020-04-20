package ro.sdi.lab.client.view.commands.movie;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.core.exception.ProgramException;

@Command(description = "Delete a movie", name = "delete")
public class DeleteMovieCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie id")
    int id;

    @Override
    public void run()
    {
        try
        {
            Console.movieController.deleteMovie(id);
            System.out.println("Movie deleted!");
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
