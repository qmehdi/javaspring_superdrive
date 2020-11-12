package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteStorageService;
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
public class NoteUploadController {

    private final NoteStorageService noteStorageService;
    private final UserService userService;
    private Authentication authentication;

    // Constructor
    public NoteUploadController(NoteStorageService noteStorageService, UserService userService) {
        this.noteStorageService = noteStorageService;
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

    @RequestMapping(value = "/notes-getall", method = { RequestMethod.GET, RequestMethod.POST })
    public String getAllNotes(@ModelAttribute("newNotemsg") NoteForm noteForm, HttpServletRequest request, Model model) {

        model.addAttribute("list_of_stored_notes", noteStorageService.getNotesUniverse());

        return "notes";
    }

    @PostMapping("/note-upload")
    public String handleNoteUpload(@ModelAttribute("newNotemsg") NoteForm noteForm, Model model) {

        // Get current time
        String uploadedtime = Instant.now().toString();

        // Note model
        NoteForm userSubmittedNote = new NoteForm(noteForm.getNotetitle(), noteForm.getNotedescription());

        // Insert operation
        noteStorageService.insertNoteIntoDB(userSubmittedNote);

        // Display in html
//        noteStorageService.getAllNotesFromDB(getLoggedInUserObject().getUserId());

        return "forward:/home";
    }
}
