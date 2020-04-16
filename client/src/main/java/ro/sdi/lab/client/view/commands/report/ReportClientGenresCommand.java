package ro.sdi.lab.client.view.commands.report;

import picocli.CommandLine.Command;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.core.exception.ProgramException;
import ro.sdi.lab.core.model.dto.ClientGenre;

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