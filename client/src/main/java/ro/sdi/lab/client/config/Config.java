package ro.sdi.lab.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import ro.sdi.lab.client.view.ResponseBuffer;
import ro.sdi.lab.common.controller.ClientController;
import ro.sdi.lab.common.controller.Controller;
import ro.sdi.lab.common.controller.MovieController;
import ro.sdi.lab.common.controller.RentalController;
import ro.sdi.lab.common.networking.ServerInformation;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@PropertySources({@PropertySource(value = "classpath:timer.properties")})
@ComponentScan({"ro.sdi.lab.client.controller", "ro.sdi.lab.client.view"})
public class Config {
    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    DateTimeFormatter dateFormatter()
    {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    }

    @Value("${timer.activation}")
    private boolean timerActivation;

    @Value("${timer.delay}")
    private int delay;

    @Bean
    ResponseBuffer responseBuffer()
    {
        return new ResponseBuffer(delay, timerActivation);
    }

    @Bean
    RmiProxyFactoryBean rmiControllerProxyFactoryBean()
    {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(Controller.class);
        String url = String.format("rmi://localhost:%d/Controller", ServerInformation.PORT);
        rmiProxyFactoryBean.setServiceUrl(url);
        return rmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rmiClientProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(ClientController.class);
        String url = String.format("rmi://localhost:%d/ClientController", ServerInformation.PORT);
        rmiProxyFactoryBean.setServiceUrl(url);
        return rmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rmiMovieProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(MovieController.class);
        String url = String.format("rmi://localhost:%d/MovieController", ServerInformation.PORT);
        rmiProxyFactoryBean.setServiceUrl(url);
        return rmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rmiRentalProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(RentalController.class);
        String url = String.format("rmi://localhost:%d/RentalController", ServerInformation.PORT);
        rmiProxyFactoryBean.setServiceUrl(url);
        return rmiProxyFactoryBean;
    }
}
