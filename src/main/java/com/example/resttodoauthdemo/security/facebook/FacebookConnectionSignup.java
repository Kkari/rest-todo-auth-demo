package com.example.resttodoauthdemo.security.facebook;

import com.example.resttodoauthdemo.security.Role;
import com.example.resttodoauthdemo.user.User;
import com.example.resttodoauthdemo.user.UserRepository;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

    private final UserRepository userRepository;

    public FacebookConnectionSignup(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String execute(Connection<?> connection) {
        Facebook facebook = (Facebook) connection.getApi();
        final User user = new User();
        user.setEmail(connection.getDisplayName());
        user.setPassword("-");
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        return user.getEmail();
    }
}
