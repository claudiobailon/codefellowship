package com.claudio.codefellowship.controllers;

import com.claudio.codefellowship.models.post.Post;
import com.claudio.codefellowship.models.post.PostRepository;
import com.claudio.codefellowship.models.user.ApplicationUser;
import com.claudio.codefellowship.models.user.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class PostController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/post")
    public RedirectView createPost(Principal principal, String body){
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        Post post = new Post(user, body);
        postRepository.save(post);
        return new RedirectView("/profile");
    }
    @GetMapping("/feed")
    public String showFeed(Model m, Principal principal){
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        m.addAttribute("user",user);
        m.addAttribute("principal",principal);
        return "feed";
    }
}
