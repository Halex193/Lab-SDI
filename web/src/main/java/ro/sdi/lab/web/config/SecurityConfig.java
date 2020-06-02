package ro.sdi.lab.web.config;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import ro.sdi.lab.core.model.UserRole;
import ro.sdi.lab.core.repository.UserRepository;
import ro.sdi.lab.web.security.AppUserDetailsService;
import ro.sdi.lab.web.security.MySavedRequestAwareAuthenticationSuccessHandler;
import ro.sdi.lab.web.security.RestAuthenticationEntryPoint;

/**
 * Created by radu.
 *
 * http://www.baeldung.com/securing-a-restful-web-service-with-spring-security
 *
 * curl -i -X POST -d username=student -d password=student -c /home/radu/cookies.txt http://localhost:8080/login
 *
 * curl -i --header "Accept:application/json" -X GET -b /home/radu/cookies.txt http://localhost:8080/api/students
 *
 */

@Configuration
@EnableWebSecurity
@ComponentScan("ro.sdi.lab.web.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler
            mySavedRequestAwareAuthenticationSuccessHandler;

    @Autowired
    private AppUserDetailsService appUserDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception
    {

        auth.authenticationProvider(authProvider());
//        auth.inMemoryAuthentication()
//                .withUser("teacher").password("pass").roles("TEACHER")
//                .and()
//                .withUser("student").password("pass").roles("STUDENT");
    }


    @Override
    protected UserDetailsService userDetailsService() {
        return appUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .cors().and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
                .and()
                .authorizeRequests()
                .antMatchers("/api/clients").hasAnyRole(UserRole.ADMIN.toString(), UserRole.BASIC.toString())
                .antMatchers("/api/movies").hasAnyRole(UserRole.ADMIN.toString(), UserRole.BASIC.toString())
                .antMatchers("/api/rentals").hasAnyRole(UserRole.ADMIN.toString(), UserRole.BASIC.toString())
                .antMatchers("/api/statistics/*").hasRole(UserRole.ADMIN.toString())
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/logout").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginProcessingUrl("/api/login")
                .successHandler(mySavedRequestAwareAuthenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler());
    }

    @Bean
    public MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler() {
        return mySavedRequestAwareAuthenticationSuccessHandler;
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Collections.singletonList("x-auth-token"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

