package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestException;
import java.time.Instant;

@Controller
public class FileUploadController {

    private final FileStorageService storageService;
    private final UserService userservice;
    private Authentication authentication;

    // Constructor
    public FileUploadController(FileStorageService storageService, UserService userservice) {

        this.storageService = storageService;
        this.userservice = userservice;
    }

    // There was a discussion about making this into a @PostConstruct but spring security will not work since at the time
    // the application starts, there is no user available in the spring context, until the user logs in.
    public User getLoggedInUserObject() {

        // Get authentication object from Spring Security
        authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get loggedinuser's username from authentication object
        String loggedinUser = authentication.getName();

        // Pass in the username to retrieve the loggedinuser object from the DB
        return userservice.getUser(loggedinUser);
    }

    @GetMapping("/file-download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename) {

        // If user did not provide a file, it would return a user-friendly error message
        if (StringUtils.isEmpty(filename)) {

            return (ResponseEntity<Resource>) ResponseEntity.notFound();
        }

        // Load file from database
        File dbFile = storageService.getSingleFile(filename, getLoggedInUserObject().getUserId());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFilename() + "\"")
                .body(new ByteArrayResource(dbFile.getFiledata()));
    }

    @RequestMapping(value = "/file-delete/{fileid}", method = RequestMethod.GET)
    public String deleteFile(@PathVariable Integer fileid) {

        storageService.deleteFileFromDB(fileid, getLoggedInUserObject().getUserId());

        return "forward:/home";
    }

    @PostMapping("/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload")
                                   MultipartFile fileUpload, Model model, HttpServletRequest request) throws IOException {

        if (fileUpload.getSize() == 0) {

            request.setAttribute("fileEmptyError", "File Empty!");
            return "forward:/home";
        }

        InputStream fis = fileUpload.getInputStream();

        // Get current time
        String uploadedtime = Instant.now().toString();

        // File model
        File userSubmittedFile = new File(fileUpload.getOriginalFilename(), fileUpload.getContentType(), Long.toString(fileUpload.getSize()), getLoggedInUserObject().getUserId(), fis.readAllBytes(), uploadedtime.toString());

        // Insert the user given file into the DB
        String insertFile = storageService.insertFileIntoDB(userSubmittedFile);

        // If the above returns an error, request.setAttribute for fileUploadfailure: true and display in html
        // Check if the returned string is fileUploadSuccess or fileUploadFailure failure and set the http attribute accordingly
        if (insertFile == "true") {

            // File insert succeeded
            request.setAttribute("fileUploadSuccess", "File Uploaded Successfully - It should be displayed below.");
        } else {

            // File insert failed
            request.setAttribute("fileUploadFailure", "File already exists in the DB, Upload Failed!");
        }

        return "forward:/home";
    }

}
