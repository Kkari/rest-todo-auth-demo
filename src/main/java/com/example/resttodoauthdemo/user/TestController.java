package com.example.resttodoauthdemo.user;

import com.example.resttodoauthdemo.security.ExampleUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

    @GetMapping
    @RequestMapping("/")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", ((ExampleUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return "index";
    }
}
