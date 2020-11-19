package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        String signupError = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "Signup Error: The username already exists.";

            // home.html looks for "signupError" attribute and flashes the message contained in signupError variable
            request.setAttribute("signupError", signupError);
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "Signup Error: There was an error signing you up. Please try again.";

                // home.html looks for "signupError" attribute and flashes the message contained in signupError variable
                request.setAttribute("signupError", signupError);
            }
        }

        if (signupError == null) {
            request.setAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "forward:/login";
    }
}