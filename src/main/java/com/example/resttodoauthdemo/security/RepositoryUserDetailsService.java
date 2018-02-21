package com.example.resttodoauthdemo.security;

import com.example.resttodoauthdemo.user.User;
import com.example.resttodoauthdemo.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

public class RepositoryUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public RepositoryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("no found");
        }

        ExampleUserDetails principal = new ExampleUserDetails(
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")),
                user.getPassword(),
                user.getEmail()
        );

        return principal;
    }
}
