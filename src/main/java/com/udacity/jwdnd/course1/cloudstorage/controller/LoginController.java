package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @RequestMapping(value="/login", method={ RequestMethod.POST, RequestMethod.GET })
    public String loginView(HttpServletRequest request) {
        request.setAttribute("signupSuccess", request.getAttribute("signupSuccess"));
        return "login";
    }
}