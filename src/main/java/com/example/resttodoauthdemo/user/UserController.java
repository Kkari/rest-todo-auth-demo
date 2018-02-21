package com.example.resttodoauthdemo.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @RequestMapping("/users")
    public void SignUp(@RequestBody SignUpDto signUpDto) {
        userService.register(signUpDto);
    }
}
