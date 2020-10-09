package com.claudio.codefellowship.controllers;


import com.claudio.codefellowship.models.user.ApplicationUser;
import com.claudio.codefellowship.models.user.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Date;
import java.util.List;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String showSignup(Principal principal, Model m){
        m.addAttribute("principal", principal);
        return "signup";
    }

    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @PostMapping("/signup")
    public ModelAndView makeNewUser(HttpServletRequest request,
                                    String username,
                                    String password,
                                    String firstName,
                                    String lastName,
                                    Date dateOfBirth,
                                    String bio,
                                    String profileImg) throws ServletException {

        password = passwordEncoder.encode(password);

        ApplicationUser newUser = new ApplicationUser(username, password, firstName, lastName, dateOfBirth, bio, profileImg);

        applicationUserRepository.save(newUser);

        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return new ModelAndView("redirect:/login");

    }

    @GetMapping("/login")
    public String login(Principal principal, Model m){
        m.addAttribute("user", principal);
        return "login";
    }

    @GetMapping("/user/{id}")//Todo:Maybe make this by username not id
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

    @GetMapping("/users")
    public String showUsers(Model m, Principal principal) {
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        List<ApplicationUser> allUsers = applicationUserRepository.findAll();
        m.addAttribute("user", user);
        m.addAttribute("principal", principal);
        m.addAttribute("allUsers", allUsers);
        return "users";
    }

    @PostMapping("/follow")
    public RedirectView followUser(Principal principal, Long id) {
        ApplicationUser userToFollow = applicationUserRepository.getOne(id);
        ApplicationUser follower = applicationUserRepository.findByUsername(principal.getName());
        userToFollow.getFollower(follower);
        follower.follow(userToFollow);
        applicationUserRepository.save(userToFollow);
        applicationUserRepository.save(follower);
        return new RedirectView("/user/" + id);
    }

    @PostMapping("/unfollow")
    public RedirectView unfollowUser(Principal principal, Long id) {
        ApplicationUser userToUnfollow = applicationUserRepository.getOne(id);
        ApplicationUser follower = applicationUserRepository.findByUsername(principal.getName());
        userToUnfollow.removeFollower(follower);
        follower.unFollow(userToUnfollow);
        applicationUserRepository.save(userToUnfollow);
        applicationUserRepository.save(follower);
        return new RedirectView("/user/" + id);
    }
}
