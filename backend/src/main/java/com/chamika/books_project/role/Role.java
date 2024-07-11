package com.chamika.books_project.role;

import com.chamika.books_project.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_role")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_sequence")
    @SequenceGenerator(name = "role_id_sequence", sequenceName = "role_id_sequence", allocationSize = 1)
    private Integer id;


    @Column(nullable = false, unique = true)
    private String roleName;


    // all the users with this role
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    // when an instance of Role is serialized to JSON, the users field will not be included in the resulting JSON.
    private List<User> users;


    // audit fields -->
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column( insertable = false)
    private LocalDateTime lastModifiedDate;

}
