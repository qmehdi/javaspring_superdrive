package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    private FileStorageService fileStorageService;
    private NoteStorageService noteStorageService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private Authentication authentication;
    private final UserService userService;

    // Constructor
    public HomeController(FileStorageService fileStorageService, NoteStorageService noteStorageService, CredentialService credentialService, EncryptionService encryptionService, UserService userService) {
        this.fileStorageService = fileStorageService;
        this.noteStorageService = noteStorageService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    @RequestMapping(value="/home", method={ RequestMethod.GET, RequestMethod.POST })
    public String homeView(@ModelAttribute("newNotemsg") NoteForm noteForm,
                           @ModelAttribute("newCredentialmsg") Credential credentialForm,
                           HttpServletRequest request, Model model) {

        authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get loggedinuser from authentication object
        String loggedinUser = authentication.getName();

        // Return the loggedinuser object from the DB
        User user = userService.getUser(loggedinUser);

        Integer loggedInUserId = user.getUserId();

        model.addAttribute("encryptionService", encryptionService);

        model.addAttribute("list_of_stored_credentials", credentialService.getAllCredentialsFromDB(loggedInUserId));

        model.addAttribute("list_of_stored_notes", noteStorageService.getAllNotesFromDB(loggedInUserId));

        model.addAttribute("list_of_stored_files", fileStorageService.getAllFilesFromDB(loggedInUserId));

        // This is to enable the "File Uploaded Successfully" message to show up on home.html
        request.setAttribute("fileUploadSuccess", request.getAttribute("fileUploadSuccess"));

        return "home";
    }
}
