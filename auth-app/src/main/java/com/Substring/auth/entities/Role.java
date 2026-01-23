package com.Substring.auth.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@Builder

@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
}

