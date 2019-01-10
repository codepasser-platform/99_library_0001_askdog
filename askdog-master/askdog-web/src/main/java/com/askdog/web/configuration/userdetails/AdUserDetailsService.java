package com.askdog.web.configuration.userdetails;

import com.askdog.model.entity.User;
import com.askdog.service.UserService;
import com.askdog.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.String.format;

@Component
public class AdUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AdUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser;
        try {
            foundUser = username.contains("@") ? userService.findByEmail(username) : userService.findByName(username);
            if (!foundUser.isPresent()) {
                throw new UsernameNotFoundException(format("User [%s] not found.", username));
            }
            return new AdUserDetails(userService.decorate(foundUser.get().getUuid()));
        } catch (ServiceException e) {
            logger.error("retrieve user failed", e);
            throw new UsernameNotFoundException(format("Can not retrieve user [%s].", username));
        }
    }
}
