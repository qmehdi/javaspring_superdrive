package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

@Controller
public class FileUploadController {

    private final FileStorageService storageService;
    private final UserService userservice;
    private Authentication authentication;

    public FileUploadController(FileStorageService storageService, UserService userservice) {

        this.storageService = storageService;
        this.userservice = userservice;
    }

    @RequestMapping(value = "/file-delete/{filename}", method = RequestMethod.GET)
    public String deleteFile(@PathVariable String filename) {

        // Get authentication object from Spring Security
        authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get loggedinuser from authentication object
        String loggedinUser = authentication.getName();

        // Pass in the username to retrieve the loggedinuser object from the DB
        User user = userservice.getUser(loggedinUser);

        storageService.deleteFileFromDB(filename, user.getUserId());

        return "forward:/home";
    }

    @PostMapping("/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload")
                                   MultipartFile fileUpload, Model model, HttpServletRequest request) throws IOException {

        InputStream fis = fileUpload.getInputStream();

        // Get current time
        String uploadedtime = Instant.now().toString();

        // Get authentication object from Spring Security
        authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get loggedinuser from authentication object
        String loggedinUser = authentication.getName();

        // Return the loggedinuser object from the DB
        User user = userservice.getUser(loggedinUser);

        // File model
        File userSubmittedFile = new File(fileUpload.getOriginalFilename(), fileUpload.getContentType(), Long.toString(fileUpload.getSize()), user.getUserId().toString(), fis.readAllBytes(), uploadedtime.toString());

        // Insert the user given file into the DB
        storageService.insertFileIntoDB(userSubmittedFile);

        request.setAttribute("fileUploadSuccess", true);

        return "forward:/home";
    }

}
