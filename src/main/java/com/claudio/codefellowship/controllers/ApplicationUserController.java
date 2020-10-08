package com.claudio.codefellowship.controllers;


import com.claudio.codefellowship.models.user.ApplicationUser;
import com.claudio.codefellowship.models.user.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
                                    String password,
                                    String firstName,
                                    String lastName,
                                    Date dateOfBirth,
                                    String bio,
                                    String profileImg){

        password = passwordEncoder.encode(password);

        ApplicationUser newUser = new ApplicationUser(username, password, firstName, lastName, dateOfBirth, bio, profileImg);

        applicationUserRepository.save(newUser);

        return new RedirectView("/login");

    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/user/{id}")
    public String showUser(Model m, Principal principal, @PathVariable long id){
        ApplicationUser user = applicationUserRepository.getOne(id);
        m.addAttribute("user", user);
        m.addAttribute("principal", principal);
        return "user";
    }

    @GetMapping("/profile")
    public String showProfile(Model m, Principal principal){
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        m.addAttribute("user",user);
        m.addAttribute("principal", principal);
        return "profile";
    }


}
