package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;
    private Authentication authentication;

    // Constructor
    public CredentialController(CredentialService credentialService, UserService userService) {

        this.credentialService = credentialService;
        this.userService = userService;
    }

    public User getLoggedInUserObject() {

        // Get authentication object from Spring Security
        authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get loggedinuser's username from authentication object
        String loggedinUser = authentication.getName();

        // Pass in the username to retrieve the loggedinuser object from the DB
        return userService.getUser(loggedinUser);
    }

    @PostMapping("/credential-create")
    public String createCredential(@ModelAttribute("newCredentialmsg") Credential credentialForm, Model model) {

        // hardcoding key to xyz for now
        Credential userSubmittedCredential = new Credential(credentialForm.getUrl(), credentialForm.getUsername(), "xyz", credentialForm.getPassword(), getLoggedInUserObject().getUserId());

        credentialService.insertCredentialIntoDB(userSubmittedCredential);

        return "forward:/home";
    }
}
