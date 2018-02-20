package com.example.resttodoauthdemo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class User {

    @GetMapping("/principal")
    public Authentication getUser(Principal principal) {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
