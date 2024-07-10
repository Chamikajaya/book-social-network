package com.chamika.books_project.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@EntityListeners(AuditingEntityListener.class)
//  AuditingEntityListener is a class provided by Spring Data JPA. It's a callback class that's used to automatically set values for certain fields, such as createdDate, lastModifiedDate, createdBy, and lastModifiedBy, when we persist or update JPA entities.
@Table(name = "t_user")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Principal, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
    @SequenceGenerator(name = "user_id_sequence", sequenceName = "user_id_sequence", allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isAccountLocked;

    @Column(nullable = false)
    private Boolean isEnabled;


    // audit fields -->
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(nullable = false, insertable = false)
    private LocalDateTime lastModifiedDate;


    @Override
    public String getName() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO: implement the body after defining Roles
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    // for getting the full name
    private String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
