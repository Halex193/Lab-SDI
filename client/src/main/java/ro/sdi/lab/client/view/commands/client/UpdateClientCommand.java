package ro.sdi.lab.client.view.commands.client;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.core.exception.ProgramException;

@Command(description = "Update a client", name = "update")
public class UpdateClientCommand implements Runnable
{
    @Parameters(index = "0", description = "Client id")
    int id;

    @Parameters(index = "1", description = "Client name")
    String name;

    @Override
    public void run()
    {
        try
        {
            Console.clientController.updateClient(id, name);
            System.out.println("Client updated!");
        }
        catch (ProgramException e)
        {
            Console.handleException(e);
        }
    }
}
