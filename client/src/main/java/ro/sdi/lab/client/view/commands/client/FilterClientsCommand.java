package ro.sdi.lab.client.view.commands.client;

import picocli.CommandLine;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.core.exception.ProgramException;
import ro.sdi.lab.core.model.Client;

@CommandLine.Command(description = "Filter clients by name", name = "filter")
public class FilterClientsCommand implements Runnable
{
    @CommandLine.Parameters(index = "0", description = "Client name")
    String name;

    @Override
    public void run()
    {
        try
        {
            Iterable<Client> filteredClients = Console.clientController.filterClientsByName(name);
            if (!filteredClients.iterator().hasNext())
            {
                System.out.println("No clients found!");
            }
            filteredClients.forEach(
                    client -> System.out.printf("%d %s\n", client.getId(), client.getName())
            );
        } catch (ProgramException e) {
            Console.handleException(e);
        }
    }
}
