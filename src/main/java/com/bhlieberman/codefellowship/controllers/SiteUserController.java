package com.bhlieberman.codefellowship.controllers;

import com.bhlieberman.codefellowship.models.SiteUser;
import com.bhlieberman.codefellowship.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class SiteUserController {

    @Autowired
    SiteUserRepository siteUserRepo;

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
        authWithHttpServletRequest(username, hashedPW);
        return new RedirectView("/");
    }

    public void authWithHttpServletRequest(String username, String password){
        try {
            request.login(username, password);
        } catch (ServletException e) {
           throw new RuntimeException("nothing works");
        }
    }

    @PostMapping("/logout")
    public String logOut() {
        return "index";
    }
}
