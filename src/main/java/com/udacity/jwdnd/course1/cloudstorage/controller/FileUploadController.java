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

    @GetMapping("/file-download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename) {

        // Get authentication object from Spring Security
        authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get loggedinuser from authentication object
        String loggedinUser = authentication.getName();

        // Pass in the username to retrieve the loggedinuser object from the DB
        User user = userservice.getUser(loggedinUser);

        // Load file from database
        File dbFile = storageService.getSingleFile(filename, user.getUserId());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFilename() + "\"")
                .body(new ByteArrayResource(dbFile.getFiledata()));
    }

    @RequestMapping(value = "/file-delete/{fileid}", method = RequestMethod.GET)
    public String deleteFile(@PathVariable Integer fileid) {

        // Get authentication object from Spring Security
        authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get loggedinuser from authentication object
        String loggedinUser = authentication.getName();

        // Pass in the username to retrieve the loggedinuser object from the DB
        User user = userservice.getUser(loggedinUser);

        storageService.deleteFileFromDB(fileid, user.getUserId());

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
        File userSubmittedFile = new File(fileUpload.getOriginalFilename(), fileUpload.getContentType(), Long.toString(fileUpload.getSize()), user.getUserId(), fis.readAllBytes(), uploadedtime.toString());

        // Insert the user given file into the DB
        String insertFile = storageService.insertFileIntoDB(userSubmittedFile);

        // If the above returns an error, request.setAttribute for fileUploadfailure: true and display in html
        // Check if the returned string is fileUploadSuccess or fileUploadFailure failure and set the http attribute accordingly
        if (insertFile == "true") {

            // File insert succeeded
            request.setAttribute("fileUploadSuccess", true);
        } else {

            // File insert failed
            request.setAttribute("fileUploadFailure", true);
        }

        return "forward:/home";
    }

}
