package ro.sdi.lab.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main
{
    public static void main(String[] args)
    {
        new AnnotationConfigApplicationContext("ro.sdi.lab.server.config");
        System.out.println("Server started...");
    }
}
