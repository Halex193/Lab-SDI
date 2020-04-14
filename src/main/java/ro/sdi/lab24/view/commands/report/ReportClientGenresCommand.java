package ro.sdi.lab24.view.commands.report;

import picocli.CommandLine.Command;
import ro.sdi.lab24.exception.ProgramException;
import ro.sdi.lab24.model.dto.ClientGenre;
import ro.sdi.lab24.view.Console;

@Command(description = "Shows the most watched genre for each client", name = "clientgenres")
public class ReportClientGenresCommand implements Runnable
{
    @Override
    public void run()
    {
        try
        {
            Iterable<ClientGenre> clientGenres = Console.controller.getClientGenres();
            if (!clientGenres.iterator().hasNext())
            {
                System.out.println("No clients found!");
            }
            clientGenres.forEach(
                    clientGenre -> System.out.printf(
                            "%s - %s\n",
                            clientGenre.getClient().getName(),
                            clientGenre.getGenre()
                    )
            );
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}