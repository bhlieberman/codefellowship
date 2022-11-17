package com.bhlieberman.codefellowship.controllers;

import com.bhlieberman.codefellowship.models.Post;
import com.bhlieberman.codefellowship.models.SiteUser;
import com.bhlieberman.codefellowship.repositories.PostRepository;
import com.bhlieberman.codefellowship.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;

@Controller
public class SiteUserController {

    @Autowired
    SiteUserRepository siteUserRepo;

    @Autowired
    PostRepository postRepo;
    @Autowired
    HttpServletRequest request;

    @GetMapping("users/user/{id}")
    public String getUserInfo(Model m, Principal p, @PathVariable Long id) {
        SiteUser authenticatedUser = siteUserRepo.findByUsername(p.getName());
        m.addAttribute("authenticatedUsername", authenticatedUser.getUsername());

        SiteUser viewUser = siteUserRepo.findById(id).orElse(new SiteUser());
        m.addAttribute("usersIFollow", viewUser.getUsersIFollow());
        m.addAttribute("usersWhoFollowMe", viewUser.getUsersWhoFollowMe());
        m.addAttribute("viewUsername", viewUser.getUsername());
        m.addAttribute("viewUserID", viewUser.getId());
        return "user_info";
    }

    @PutMapping("users/user/{id}")
    public RedirectView editUserInfo(Model m, Principal p, @PathVariable Long id, String username, RedirectAttributes redir) throws ServletException {
        SiteUser userToBeEdited = siteUserRepo.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Couldn't find a user with that ID");
        });
        if (p.getName().equals(userToBeEdited.getUsername())) {
            userToBeEdited.setUsername(username);
            siteUserRepo.save(userToBeEdited);
            request.logout();
        } else {
            redir.addFlashAttribute("errorMessage", "Cannot edit another user's info");
        }
        return new RedirectView("/user/" + id);
    }

    @GetMapping("users/user/{userId}/posts")
    String getPosts(@PathVariable Long userId, Model m) {
        var posts = postRepo
                .findAll();
        m.addAttribute("posts", posts);
        return "profile";
    }

    @PostMapping("/posts")
    RedirectView createPost(@RequestParam String body, @RequestParam String timestamp, Principal p) {
        Post post = new Post(body, LocalDateTime.now());
        var user = siteUserRepo.findByUsername(p.getName());
        user.getPosts().add(post);
        siteUserRepo.save(user);
        postRepo.save(post);
        return new RedirectView("users/user/" + user.getId() + "/posts");
    }

    @PostMapping("logout")
    public String logOut() {
        return "index";
    }

    @PutMapping("/follow-user/{id}")
    public RedirectView followUser(Principal p, @PathVariable Long id){
        SiteUser userToFollow = siteUserRepo.findById(id).orElseThrow(() -> new RuntimeException("Error retrieving user from the database with an ID of: " + id));
        SiteUser browsingUser = siteUserRepo.findByUsername(p.getName());
        if(browsingUser.getUsername().equals(userToFollow.getUsername())){
            throw new IllegalArgumentException("Can't follow your own account");
        }
        browsingUser.getUsersIFollow().add(userToFollow);
        siteUserRepo.save(browsingUser);
        return new RedirectView("/users/user/" + id);
    }
}
