package com.example.resttodoauthdemo.user;

import com.example.resttodoauthdemo.security.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void register(SignUpDto signUpDto) {
        userRepository.save(new User(
                signUpDto.getEmail(),
                passwordEncoder.encode(signUpDto.getPassword()),
                Role.ROLE_USER
        ));
    }

    @PostConstruct
    public void dummyData() {
        register(new SignUpDto("karoly.kemeny@gmail.com", "almafa"));
    }
}
