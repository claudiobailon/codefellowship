package com.claudio.codefellowship.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @GetMapping("/error")//Help from https://www.logicbig.com/tutorials/spring-framework/spring-boot/servlet-error-handling-outside-mvc.html
    public String handleError(Model m, HttpServletRequest request){
        int status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        m.addAttribute("status", status);
        m.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
