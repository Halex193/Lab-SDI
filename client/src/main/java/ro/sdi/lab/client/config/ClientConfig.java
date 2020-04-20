package ro.sdi.lab.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan({"ro.sdi.lab.client.controller", "ro.sdi.lab.client.view", "ro.sdi.lab.web.converter"})
public class ClientConfig
{
    @Bean
    RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
