package com.Substring.auth.entities;




import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID ;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder




@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
     @Column(name = "user_id")
      private  UUID  id ;

      @Column(name = "email", unique = true ,length = 300)
      private String email;
      private String name;
      private String password;
      private String image ;
      private boolean enable= true ;
//    Instant represents a single point in time, with very high precision (nanoseconds).
      private Instant createdAt =  Instant.now();
      private Instant updatedat = Instant.now();
//      private String gender;
//      private Address address;

    @Enumerated(EnumType.STRING)
      private Provider  provider = Provider.Local;

      @ManyToMany(fetch = FetchType.EAGER)
      @JoinTable(name = "user_roles",
              joinColumns = @JoinColumn (name = "user_id"),
              inverseJoinColumns = @JoinColumn(name= "role_id"))
      private Set<Role> roles = new HashSet<>();

      @PrePersist
     protected  void onCreate(){
           Instant now = Instant.now();
           if(createdAt == null) createdAt = now;
           updatedat = now;
      }
    @PreUpdate
    protected void onUpdate() {
        this.updatedat = Instant.now();
    }



}
