package com.ballesteros.api.persistence.models;

import com.ballesteros.api.enums.Country;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

/**
 * Entidad que representa un equipo.
 */
@Entity
@Table(name = "T_TEAMS")
@Data
public class TeamModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "C_NAME", nullable = false)
    private String name;

    @Column(name = "C_DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "C_PLAYERS")
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PlayerModel> players;

    @Column(name = "C_COUNTRY")
    private Country country;

    @Column(name = "C_IMAGE")
    private String image;

    @Override
    public String toString() {
        return "TeamModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", players=[size=" + (players != null ? players.size() : 0) + "]" +
                '}';
    }
}
