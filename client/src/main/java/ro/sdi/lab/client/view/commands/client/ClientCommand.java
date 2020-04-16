package ro.sdi.lab.client.view.commands.client;

import picocli.CommandLine.Command;

@Command(name = "client", description = "Manage clients", subcommands = {
        AddClientCommand.class,
        ListClientsCommand.class,
        UpdateClientCommand.class,
        DeleteClientCommand.class,
        FilterClientsCommand.class
},mixinStandardHelpOptions = true)
public class ClientCommand implements Runnable
{
    @Override
    public void run()
    {
            System.out.println("Use a subcommand! Type 'client --help'");
    }
}
