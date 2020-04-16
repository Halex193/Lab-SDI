package ro.sdi.lab.client.view.commands.rental;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.core.exception.ProgramException;

@Command(description = "Delete rental", name = "delete")
public class DeleteRentalCommand implements Runnable
{
    @CommandLine.Parameters(index = "0", description = "Movie id")
    int movieId;

    @CommandLine.Parameters(index = "1", description = "Client id")
    int clientId;

    @Override
    public void run()
    {
        try
        {
            Console.rentalController.deleteRental(movieId, clientId);
            System.out.println("Rental deleted!");
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
