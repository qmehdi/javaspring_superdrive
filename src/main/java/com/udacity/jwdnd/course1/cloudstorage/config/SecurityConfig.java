package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }

    // This method override allows us to access the db via the web ui at localhost:8080/h2-console
    // The WebSecurity layer is outer than the HttpSecurity layer so doing this allows us to get to the /h2-console
    // url without logging in to the web application
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Start of the builder pattern
        http.authorizeRequests()

                // Permit all requests to the /signup page, as well as to all files in /css and /js directories.
                .antMatchers("/signup", "/css/**", "/js/**").permitAll()

                // Allow only authenticated users to any pages not specifically identified.
                .anyRequest().authenticated();

        // .formLogin() means when an unauthenticated user tries to get to any page not previously mentioned, redirect them to the following
        // Always put the "/login" as part of http.formLogin(), instead of above in .antMatchers -- Good security practice
        http.formLogin()
                .loginPage("/login")
                .permitAll();

        http.formLogin()
                // After a successful login, redirect authenticated users
                .defaultSuccessUrl("/home", true)

                // Redirect them to this page on a failed login
                // No controller exists yet
                .failureUrl("/error");

        // Add a logout page, in order to use this you must also create a /logout controller and a logout.html
        // This doesn't work yet
//        http.logout();
//                .logoutUrl("/logout")
//                .invalidateHttpSession(true);
    }
}