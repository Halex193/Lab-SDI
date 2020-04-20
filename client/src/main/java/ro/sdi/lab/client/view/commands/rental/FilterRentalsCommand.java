package ro.sdi.lab.client.view.commands.rental;


import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.core.exception.ProgramException;
import ro.sdi.lab.core.model.Rental;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Command(description = "Filter rentals by movie name", name = "filter")
public class FilterRentalsCommand implements Runnable
{
    @Parameters(index = "0", description = "Movie name")
    String name;

    @Override
    public void run()
    {
        try
        {
            Iterable<Rental> filteredRentals = Console.rentalController.filterRentalsByMovieName(
                    name);
            if (!filteredRentals.iterator().hasNext())
            {
                System.out.println("No rentals found!");
            }
            filteredRentals.forEach(
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
