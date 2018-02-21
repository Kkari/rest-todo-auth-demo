package com.example.resttodoauthdemo.security;

import com.example.resttodoauthdemo.security.facebook.FacebookConnectionSignup;
import com.example.resttodoauthdemo.security.facebook.FacebookSignInAdapter;
import com.example.resttodoauthdemo.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final ConnectionFactoryLocator connectionFactoryLocator;
    private final FacebookConnectionSignup facebookConnectionSignup;
    private final UsersConnectionRepository usersConnectionRepository;
    private final UserRepository userRepository;

    public SecurityConfiguration(DataSource dataSource,
                                 ConnectionFactoryLocator connectionFactoryLocator,
                                 FacebookConnectionSignup facebookConnectionSignup,
                                 UsersConnectionRepository usersConnectionRepository,
                                 UserRepository userRepository) {
        this.dataSource = dataSource;
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.facebookConnectionSignup = facebookConnectionSignup;
        this.usersConnectionRepository = usersConnectionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/login*","/signin/**","/signup/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/users").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .rememberMe()
                    .rememberMeCookieName("example-remember-me")
                    .alwaysRemember(true)
                    .tokenRepository(persistentTokenRepository())
                .and()
                    .formLogin().loginPage("/login").successForwardUrl("/").permitAll()
                .and()
                    .logout();
    }

    @Bean
    PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    JdbcUsersConnectionRepository jdbcUsersConnectionRepository() {
        return new JdbcUsersConnectionRepository(
                dataSource,
                connectionFactoryLocator,
                Encryptors.noOpText()
        );
    }

    @Bean
    public ProviderSignInController providerSignInController() {
        ((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(facebookConnectionSignup);
        return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, new FacebookSignInAdapter(userRepository));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new RepositoryUserDetailsService(userRepository);
    }
}

