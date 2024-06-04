package com.ballesteros.api.persistence.models;

import com.ballesteros.api.enums.HissatsuTechniquesType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

/**
 * Entidad que representa una supertécnica.
 */
@Entity
@Table(name = "T_HISSATSU_TECHNIQUES")
@Data
public class HissatsuTechniquesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "C_NAME", nullable = false)
    private String name;

    @Column(name = "C_TYPE", nullable = false)
    private HissatsuTechniquesType type;

    @Column(name = "C_POWER", nullable = false)
    private int power;

    @ManyToMany(mappedBy = "hissatsuTechniques")
    @JsonIgnore
    private List<PlayerModel> players;

    @Override
    public String toString() {
        return name;
    }

}
