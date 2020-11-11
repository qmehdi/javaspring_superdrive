package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
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
    private Authentication authentication;
    private final UserService userService;

    // Constructor
    public HomeController(FileStorageService fileStorageService, UserService userService) {
        this.fileStorageService = fileStorageService;
        this.userService = userService;
    }

    @RequestMapping(value="/home", method={ RequestMethod.GET, RequestMethod.POST })
    public String homeView(@ModelAttribute("newNotemsg") NoteForm noteForm, HttpServletRequest request, Model model) {

        authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get loggedinuser from authentication object
        String loggedinUser = authentication.getName();

        // Return the loggedinuser object from the DB
        User user = userService.getUser(loggedinUser);

        model.addAttribute("list_of_stored_files", fileStorageService.getAllFilesFromDB(user.getUserId()));

        // This is to enable the "File Uploaded Successfully" message to show up on home.html
        request.setAttribute("fileUploadSuccess", request.getAttribute("fileUploadSuccess"));

        return "home";
    }
}
