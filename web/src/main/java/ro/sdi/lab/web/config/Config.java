package ro.sdi.lab.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ro.sdi.lab.core.config.JPAConfig;
import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.model.Movie;
import ro.sdi.lab.core.model.Rental;
import ro.sdi.lab.core.repository.Repository;
import ro.sdi.lab.core.validation.ClientValidator;
import ro.sdi.lab.core.validation.MovieValidator;
import ro.sdi.lab.core.validation.RentalValidator;

@Configuration
@ComponentScan({"ro.sdi.lab.core"})
@Import({JPAConfig.class})
@PropertySources({@PropertySource(value = "classpath:local/db.properties"),
})
public class Config
{
    /**
     * Enables placeholders usage with SpEL expressions.
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    MovieValidator movieValidator()
    {
        return new MovieValidator();
    }

    @Bean
    ClientValidator clientValidator()
    {
        return new ClientValidator();
    }

    @Bean
    DateTimeFormatter dateFormatter()
    {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    }
}
