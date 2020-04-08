package ro.sdi.lab.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutorService;

import ro.sdi.lab.client.config.Config;
import ro.sdi.lab.client.view.Console;

public class Main
{
    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class.getPackageName());

        Console.run(args);
        context.getBean(ExecutorService.class).shutdown();
    }
}
