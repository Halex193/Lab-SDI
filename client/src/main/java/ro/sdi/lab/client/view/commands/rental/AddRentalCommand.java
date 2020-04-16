package ro.sdi.lab.client.view.commands.rental;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.core.exception.ProgramException;

@Command(description = "Add a rental", name = "add")
public class AddRentalCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie id")
    int movieId;

    @Parameters(index = "1", description = "Client id")
    int clientId;

    @Parameters(index = "2", description = "Rental time: \"dd-MM-yyyy HH:mm\"")
    String time;

    @Override
    public void run()
    {
        try
        {
            Console.rentalController.addRental(movieId, clientId, time);
            System.out.println("Rental added!");
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
