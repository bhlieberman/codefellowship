package com.bhlieberman.codefellowship.controllers;

import com.bhlieberman.codefellowship.models.SiteUser;
import com.bhlieberman.codefellowship.repositories.SiteUserRepository;
import com.bhlieberman.codefellowship.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;
import java.util.Optional;


@Controller("/")
public class HomeController {

    @Autowired
    AuthService auth = new AuthService();

    @Autowired
    SiteUserRepository siteUserRepository;

    @GetMapping
    public String getHomePage(Model m, Principal p) {
        String username = p != null ? p.getName() : "";
        Optional<SiteUser> user = Optional.ofNullable(siteUserRepository.findByUsername(username));
        m.addAttribute("username", username);
        m.addAttribute("fName",
                user.map(SiteUser::getFirstName)
                        .orElse("not a user")
        );
        return "index";
    }

    @GetMapping("login")
    String getLogin() {
        return "login";
    }

    @GetMapping("signup")
    String getSignupPage() {
        return "signup";
    }

    @PostMapping("signup")
    RedirectView createUser(String username, String password, String firstName, String lastName) {
        String hashedPW = auth.hashPassword(password);
        SiteUser newUser = new SiteUser(username, hashedPW, firstName, lastName);
        siteUserRepository.save(newUser);
        auth.authWithHttpServletRequest(username, password);
        return new RedirectView("/");
    }

}
