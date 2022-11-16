package com.bhlieberman.codefellowship.controllers;

import com.bhlieberman.codefellowship.models.Post;
import com.bhlieberman.codefellowship.models.SiteUser;
import com.bhlieberman.codefellowship.repositories.PostRepository;
import com.bhlieberman.codefellowship.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
public class SiteUserController {

    @Autowired
    SiteUserRepository siteUserRepo;

    @Autowired
    PostRepository postRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/")
    public String getHomePage(Model m, Principal p){
        if (p != null) {
            String username = p.getName();
            SiteUser foundUser = siteUserRepo.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("fName", foundUser.getFirstName());
        }
        return "index";
    }

    @GetMapping("/login")
    String getLogin() {
        return "login";
    }

    @GetMapping("/signup")
    String getSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    RedirectView createUser(String username, String password, String firstName, String lastName) {
        String hashedPW = passwordEncoder.encode(password);
        SiteUser newUser = new SiteUser(username, hashedPW, firstName, lastName);
        siteUserRepo.save(newUser);
        authWithHttpServletRequest(username, password);
        return new RedirectView("/");
    }

    @GetMapping("/profile")
    public String getProfile() {
        return "profile";
    }

    @GetMapping("/users/{id}")
    public String getUserInfo(Model m, Principal p, @PathVariable Long id) {
        SiteUser authenticatedUser = siteUserRepo.findByUsername(p.getName());
        m.addAttribute("authenticatedUsername", authenticatedUser.getUsername());

        SiteUser viewUser = siteUserRepo.findById(id).orElseThrow();
        m.addAttribute("viewUsername", viewUser.getUsername());
        m.addAttribute("viewUserID", viewUser.getId());
        return "/user_info";
    }

    @GetMapping("/profile/{id}")
    public String getPosts(Model m, @PathVariable Long id) {
        List<Post> posts = postRepo.findPostsById(id).orElse(Collections.emptyList());
        m.addAttribute("posts", posts);
        return "profile";
    }

    @PostMapping("/profile/")
    public String createPost(Principal p, String body, LocalDateTime timestamp, @PathVariable Long id) {
        if (p.getName() != null) {
            Post post = new Post(body, timestamp);
            postRepo.save(post);
        }
        return "profile";
    }

    @PutMapping("/users/{id}")
    public RedirectView editUserInfo(Model m, Principal p, @PathVariable Long id, String username, RedirectAttributes redir) throws ServletException {
        SiteUser userToBeEdited = siteUserRepo.findById(id).orElseThrow();
        if (p.getName().equals(userToBeEdited.getUsername())) {
            userToBeEdited.setUsername(username);
            siteUserRepo.save(userToBeEdited);
            request.logout();
        } else {
            redir.addFlashAttribute("errorMessage", "Cannot edit another user's info");
        }
        return new RedirectView("/users/" + id);
    }

    public void authWithHttpServletRequest(String username, String password){
        try {
            request.login(username, password);
        } catch (ServletException e) {
           e.getRootCause();
        }
    }

    @PostMapping("/logout")
    public String logOut() {
        return "index";
    }
}
