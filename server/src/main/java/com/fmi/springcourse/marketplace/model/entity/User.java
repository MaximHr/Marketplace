package com.fmi.springcourse.marketplace.model.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.rmi.server.UID;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter @Setter
public class User extends SluggableEntity {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String username;

    @Email @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password; // will store the hashed password

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private void validate(Long id, String username, String email, String password, UserRole role) {
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("Id must be a positive number");
        }

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (role == null ) {
            throw new IllegalArgumentException("Role cannot be null");
        }
    }

    public User(Long id, String username, String email, String password, UserRole role) {
        validate(id, username, email, password, role);

        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String username, String email, String password, UserRole role) {
        this(null, username, email, password, role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
