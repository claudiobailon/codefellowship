package com.claudio.codefellowship.controllers;


import com.claudio.codefellowship.models.user.ApplicationUser;
import com.claudio.codefellowship.models.user.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;
import java.sql.Date;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String showSignup(){
        return "signup";
    }

    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @PostMapping("/signup")
    public RedirectView makeNewUser(String username,
                                    String passwordhere,
                                    String firstName,
                                    String lastName,
                                    Date dateOfBirth,
                                    String bio){

        passwordhere = passwordEncoder.encode(passwordhere);

        ApplicationUser newUser = new ApplicationUser(username, passwordhere, firstName, lastName, dateOfBirth, bio);

        applicationUserRepository.save(newUser);

        return new RedirectView("/");

    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


}
