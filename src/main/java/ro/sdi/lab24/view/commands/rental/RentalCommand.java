package ro.sdi.lab24.view.commands.rental;

import picocli.CommandLine.Command;

@Command(name = "rental", description = "Manage rentals", subcommands = {
        AddRentalCommand.class,
        ListRentalsCommand.class,
        UpdateRentalCommand.class,
        DeleteRentalCommand.class,
})
public class RentalCommand implements Runnable
{
    @Override
    public void run()
    {
        System.out.println("Use a subcommand!");
    }
}
