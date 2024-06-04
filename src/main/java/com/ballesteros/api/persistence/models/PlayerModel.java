package com.ballesteros.api.persistence.models;

import com.ballesteros.api.enums.Element;
import com.ballesteros.api.enums.PlayerPosition;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un jugador.
 */
@Entity
@Table(name = "T_PLAYERS")
@Data
public class PlayerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "C_NAME", nullable = false)
    @NotBlank(message = "Name is required")
    @Size(min = 4, message = "Name must be at least 4 characters long")
    private String name;

    @Column(name = "C_NICKNAME")
    @JsonIgnore
    private String nickname;

    @Column(name = "C_POSITION", nullable = false)
    private PlayerPosition position;

    @Column(name = "C_ELEMENT", nullable = false)
    private Element element;

    @ManyToOne
    @JoinColumn(name = "C_TEAM_FK", nullable = false)
    @JsonIgnore
    private TeamModel team;

    @ManyToMany
    @JoinTable(
            name = "C_PLAYER_HISSATSU_TECHNIQUES",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "hissatsuTechnique_id")

    )
    private List<HissatsuTechniquesModel> hissatsuTechniques;

    @Column(name = "C_IMAGE")
    private String image;

    @Column(name = "C_TECHNIQUE")
    private int technique;
    @Column(name = "C_KICK")
    private int kick;
    @Column(name = "C_CONTROL")
    private int control;
    @Column(name = "C_PRESSURE")
    private int pressure;
    @Column(name = "C_AGILITY")
    private int agility;
    @Column(name = "C_PHYSICAL")
    private int physical;
    @Column(name = "C_INTELLIGENCE")
    private int intelligence;

    @ManyToMany(mappedBy = "players")
    @JsonIgnore
    private List<UserModel> users = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }
}

