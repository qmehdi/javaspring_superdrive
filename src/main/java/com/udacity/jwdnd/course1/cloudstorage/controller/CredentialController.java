package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;
    private Authentication authentication;

    // Constructor
    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {

        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
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

        // Encrypt the password
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);

        // Create the credential object using the form fields, encodedKey, and encryptedPassword
        Credential userSubmittedCredential = new Credential(credentialForm.getUrl(), credentialForm.getUsername(), encodedKey, encryptedPassword, getLoggedInUserObject().getUserId());

        credentialService.insertCredentialIntoDB(userSubmittedCredential);

        return "forward:/home";
    }

    @RequestMapping(value = "/credential-delete/{credentialId}", method = { RequestMethod.GET })
    public String deleteCredential(@PathVariable Integer credentialId) {

        credentialService.deleteCredential((credentialId));
        return "forward:/home";
    }

}
