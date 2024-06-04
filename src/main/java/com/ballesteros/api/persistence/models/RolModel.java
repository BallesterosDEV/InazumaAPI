package com.ballesteros.api.persistence.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

/**
 * Entidad que representa un rol.
 */
@Entity
@Table(name = "T_ROLES")
@Data
@NoArgsConstructor
public class RolModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_ID")
    Long id;

    @Column(name = "C_NAME")
    String name;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    List<UserModel> users;
}