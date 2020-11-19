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

    @PostMapping("/note-upload")
    public String handleNoteUpload(@ModelAttribute("newNotemsg") NoteForm noteForm, Model model) {

        // Get current time
        String uploadedtime = Instant.now().toString();

        // Note model
        NoteForm userSubmittedNote = new NoteForm(noteForm.getNotetitle(), noteForm.getNotedescription(), getLoggedInUserObject().getUserId());

//        // Insert operation
//        noteStorageService.insertNoteIntoDB(userSubmittedNote);

        // If noteId is not null, that means we are in edit mode
        if (noteForm.getNoteId() != null ) {

            // Invoke Update method
            // Notice that we are not sending in the constructed userSubmittedNote into the update function because we need to preserve the original noteId
            // Instead we are sending in the submitted noteForm object.
            noteStorageService.updateNoteInDB(noteForm);
        } else {

            // Invoke Insert method
            noteStorageService.insertNoteIntoDB(userSubmittedNote);
        }

        // Display in html
//        noteStorageService.getAllNotesFromDB(getLoggedInUserObject().getUserId());

        return "forward:/home";
    }

    @RequestMapping(value = "/note-delete/{noteId}", method = RequestMethod.GET)
    public String deleteNote(@PathVariable Integer noteId) {

        noteStorageService.deleteNoteFromDB(noteId, getLoggedInUserObject().getUserId());

        return "forward:/home";
    }
}
