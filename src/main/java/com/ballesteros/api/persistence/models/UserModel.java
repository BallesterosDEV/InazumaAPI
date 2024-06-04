package com.ballesteros.api.persistence.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Entidad que representa un usuario.
 */
@Entity
@Table(name = "T_USERS")
@Data
@NoArgsConstructor
public class UserModel implements Serializable, UserDetails {

    @Column(name = "C_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "C_USERNAME", nullable = false)
    @Size(min = 4, message = "Username must be at least 4 characters")
    private String username;

    @Column(name = "C_EMAIL", unique = true, nullable = false)
    @Size(min = 4, message = "Email must be at least 4 characters")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "C_PASSWORD", nullable = false)
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_ROLE", referencedColumnName = "C_ID")
    RolModel rol;

    @ManyToMany
    @JoinTable(
            name = "T_USER_PLAYERS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PLAYER_ID")
    )
    private List<PlayerModel> players = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
