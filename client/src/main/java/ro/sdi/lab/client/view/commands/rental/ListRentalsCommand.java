package ro.sdi.lab.client.view.commands.rental;

import picocli.CommandLine.Command;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.core.exception.ProgramException;
import ro.sdi.lab.core.model.Rental;

@Command(description = "List rentals", name = "list")
public class ListRentalsCommand implements Runnable
{
    @Override
    public void run()
    {
        try
        {
            Iterable<Rental> rentals = Console.rentalController.getRentals();
            if (!rentals.iterator().hasNext())
            {
                System.out.println("No rentals found!");
            }
            rentals.forEach(
                    rental -> System.out.printf(
                            "%d %d %s\n",
                            rental.getId().getMovieId(),
                            rental.getId().getClientId(),
                            rental.getTime().format(Console.rentalController.formatter)
                    )
            );
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
