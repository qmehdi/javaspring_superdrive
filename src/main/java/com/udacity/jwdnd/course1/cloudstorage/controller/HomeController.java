package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    private FileStorageService fileStorageService;
    private Authentication authentication;

    // Constructor
    public HomeController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @RequestMapping(value="/home", method={ RequestMethod.GET, RequestMethod.POST })
    public String homeView(HttpServletRequest request, Model model) {

        authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("list_of_stored_files", fileStorageService.getFileUniverse());

        // This is to enable the "File Uploaded Successfully" message to show up on home.html
        request.setAttribute("fileUploadSuccess", request.getAttribute("fileUploadSuccess"));

        return "home";
    }
}
