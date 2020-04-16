package ro.sdi.lab.client.view.commands;

import ro.sdi.lab.client.view.commands.client.ClientCommand;
import ro.sdi.lab.client.view.commands.movie.MovieCommand;
import ro.sdi.lab.client.view.commands.rental.RentalCommand;
import ro.sdi.lab.client.view.commands.report.ReportCommand;

import static picocli.CommandLine.Command;

@Command(name = "",
        subcommands = {
                ClientCommand.class,
                MovieCommand.class,
                RentalCommand.class,
                ReportCommand.class
        },
        mixinStandardHelpOptions = true)
public class MovieRentalCommand implements Runnable
{
        @Override
        public void run()
        {
                System.out.println("Use a command!");
        }
}
