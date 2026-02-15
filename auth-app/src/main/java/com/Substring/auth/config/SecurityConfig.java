package com.Substring.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/v1/auth/register").permitAll()
                .requestMatchers("/api/v1/auth/login").permitAll()
                .anyRequest().authenticated()
        ).httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }




    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService users(BCryptPasswordEncoder encoder) {
//
//
////        UserDetails user1 = User.builder()
////                .username("Vishwajeet")
////                .password(encoder.encode("abc"))
////                .roles("ADMIN")
////                .build();
////
////        UserDetails user2 = User.builder()
////                .username("Ram")
////                .password(encoder.encode("abcd"))
////                .roles("ADMIN")
////                .build();
////
////        return new InMemoryUserDetailsManager(user1, user2);
//    }
}