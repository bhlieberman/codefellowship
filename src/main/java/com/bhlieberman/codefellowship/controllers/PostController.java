package com.bhlieberman.codefellowship.controllers;

import com.bhlieberman.codefellowship.models.Post;
import com.bhlieberman.codefellowship.models.SiteUser;
import com.bhlieberman.codefellowship.repositories.PostRepository;
import com.bhlieberman.codefellowship.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;


}
