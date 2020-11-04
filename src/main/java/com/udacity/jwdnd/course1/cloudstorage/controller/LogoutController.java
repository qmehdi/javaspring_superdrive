package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutController {

    @RequestMapping(value="/logout", method={ RequestMethod.GET })
    public String logoutView(HttpServletRequest request) throws ServletException {
        request.logout();
        return "home";
    }
}
