package com.claudio.codefellowship.controllers;


import com.claudio.codefellowship.models.user.ApplicationUser;
import com.claudio.codefellowship.models.user.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/newuser")
    public RedirectView makeNewUser(String username, String passwordhere){

        passwordhere = passwordEncoder.encode(passwordhere);

        ApplicationUser newUser = new ApplicationUser(username, passwordhere);

        applicationUserRepository.save(newUser);

        return new RedirectView("/");

    }

    @GetMapping("/login")
    public String login(){
        return"login";
    }


}
