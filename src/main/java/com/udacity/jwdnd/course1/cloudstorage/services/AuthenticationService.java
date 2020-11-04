package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {
    private UserMapper userMapper;
    private HashService hashService;

    public AuthenticationService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    @Override
    // When a user submits a login form, the form object is of class Authentication along with roles
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Retrieve the credentials from the submitted form
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Get the user object from the DB
        User user = userMapper.getUser(username);
        if (user != null) {
            // This comes from the user object stored in the db
            String encodedSalt = user.getSalt();

            // Send the form submitted password and db-stored salt to the hashService which will return a hashed string
            String hashedPassword = hashService.getHashedValue(password, encodedSalt);

            // Compare the db-stored hashed pw hash string to
            // the string that's the result of combining user-submitted hashed pw and db-stored salt
            if (user.getPassword().equals(hashedPassword)) {

                // This means the user was authenticated and it will create a session for the user
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}