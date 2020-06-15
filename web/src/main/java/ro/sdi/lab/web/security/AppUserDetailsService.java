package ro.sdi.lab.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ro.sdi.lab.core.model.User;
import ro.sdi.lab.core.service.UserService;


@Component
public class AppUserDetailsService implements UserDetailsService, ApplicationListener<AuthenticationSuccessEvent>
{

    private static final Logger log = LoggerFactory.getLogger(AppUserDetailsService.class);

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.trace("loadUserByUsername:: username = {}", username);

        User user = userService.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getUserRole()));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(), true, true, true, true, authorities);
//        return new org.springframework.security.core.userdetails.User("user", "pass", true, true,
//                true, true, authorities);
    }





    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

    }


}
