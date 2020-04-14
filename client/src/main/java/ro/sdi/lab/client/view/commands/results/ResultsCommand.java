package ro.sdi.lab.client.view.commands.results;

import java.util.List;

import picocli.CommandLine;
import ro.sdi.lab.client.view.Console;

@CommandLine.Command(description = "Displays all results until now", name = "results")
public class ResultsCommand implements Runnable
{
    @Override
    public void run()
    {
        List<String> responses = Console.responseBuffer.getResponses();
        if (responses.isEmpty())
        {
            System.out.println("No results pending!");
            return;
        }
        System.out.println(String.join("\n", responses));
    }
}
