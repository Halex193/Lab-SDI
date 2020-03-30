package ro.sdi.lab24.view;

import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import picocli.CommandLine;
import ro.sdi.lab24.controller.ClientController;
import ro.sdi.lab24.controller.Controller;
import ro.sdi.lab24.controller.MovieController;
import ro.sdi.lab24.controller.RentalController;
import ro.sdi.lab24.exception.ProgramException;
import ro.sdi.lab24.view.commands.MovieRentalCommand;

public class Console
{
    public static Controller controller;
    public static ClientController clientController;
    public static MovieController movieController;
    public static RentalController rentalController;
    public static DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public static ResponseBuffer responseBuffer = new ResponseBuffer();//TODO call the add method to add a future, call getResponses() in a new command

    public static void initialize(
            Controller controller,
            ClientController clientController,
            MovieController movieController,
            RentalController rentalController
    )
    {
        Console.controller = controller;
        Console.clientController = clientController;
        Console.movieController = movieController;
        Console.rentalController = rentalController;
    }

    public static String handleException(ProgramException e)
    {
        return e.getMessage();
    }

    public static void run(String[] args)
    {
        System.out.println("Movie rental software");
        CommandLine commandLine = new CommandLine(MovieRentalCommand.class);
        commandLine.setUnmatchedOptionsArePositionalParams(true);
        commandLine.setErr(new PrintWriter(System.out));
        if (args.length == 0)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.print("> ");
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if (line.equals("exit"))
                {
                    break;
                } else if (!line.matches("^(movie|rental|client|report|--help|-h).*"))
                {
                    System.out.println("Invalid command! Type '--help'");
                } else
                {
                    commandLine.execute(parseLine(line));
                }
                System.out.print("> ");
            }
            return;
        }

        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }

    protected static String[] parseLine(String line)
    {
        List<String> matchList = new ArrayList<String>();
        Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
        Matcher regexMatcher = regex.matcher(line);
        while (regexMatcher.find())
        {
            if (regexMatcher.group(1) != null)
            {
                // Add double-quoted string without the quotes
                matchList.add(regexMatcher.group(1));
            } else if (regexMatcher.group(2) != null)
            {
                // Add single-quoted string without the quotes
                matchList.add(regexMatcher.group(2));
            } else
            {
                // Add unquoted word
                matchList.add(regexMatcher.group());
            }
        }
        return matchList.toArray(new String[0]);
    }
}
