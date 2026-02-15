package com.Substring.auth.Security;

import com.Substring.auth.exceptions.ResourceNotFoundException;
import com.Substring.auth.repositories.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {


    private final UserRespository userRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

     return userRespository.findByEmail(username).orElseThrow(() ->new ResourceNotFoundException("Invalid email or password"));


    }
}
