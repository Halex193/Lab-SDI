package ro.sdi.lab.client.view.commands.client;

import picocli.CommandLine.Command;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.core.exception.ProgramException;
import ro.sdi.lab.core.model.Client;


@Command(description = "List all clients", name = "list")
public class ListClientsCommand implements Runnable
{
    @Override
    public void run()
    {
        try
        {
            Iterable<Client> clients = Console.clientController.getClients();
            if (!clients.iterator().hasNext())
            {
                System.out.println("No clients found!");
            }
            clients.forEach(
                    client -> System.out.printf("%d %s\n", client.getId(), client.getName())
            );
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
