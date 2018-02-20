package com.example.resttodoauthdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class Resource {
    @GetMapping("/resource")
    public String getInfo(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
