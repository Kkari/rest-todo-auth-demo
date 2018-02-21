package com.example.resttodoauthdemo.security.facebook;

import com.example.resttodoauthdemo.security.ExampleUserDetails;
import com.example.resttodoauthdemo.security.Role;
import com.example.resttodoauthdemo.user.User;
import com.example.resttodoauthdemo.user.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Arrays;

public class FacebookSignInAdapter implements SignInAdapter {

    private final UserRepository userRepository;

    public FacebookSignInAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String signIn(String s, Connection<?> connection, NativeWebRequest nativeWebRequest) {
        User user = userRepository.findByEmail(connection.getDisplayName());


        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        new ExampleUserDetails(
                                Arrays.asList(new SimpleGrantedAuthority(user.getRole().toString())),
                                user.getPassword(),
                                user.getEmail()
                        ),
                        null,
                        Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_USER.toString()))
                ));
        return null;
    }
}
