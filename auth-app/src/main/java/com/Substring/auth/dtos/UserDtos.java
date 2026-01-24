package com.Substring.auth.dtos;


import com.Substring.auth.entities.Provider;
import com.Substring.auth.entities.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtos {

    private UUID id ;
    private String email;
    private String name;
    private String password;
    private String image ;
    private boolean enable= true ;
    private Instant createdAt =  Instant.now();
    private Instant updatedat = Instant.now();
    private Provider provider = Provider.Local;
    private Set<RoleDto> roles = new HashSet<>();
}
