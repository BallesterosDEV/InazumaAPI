package com.ballesteros.api.persistence.repositories;

import com.ballesteros.api.persistence.models.PlayerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para entidades PlayerModel.
 */
@Repository
public interface PlayerRepository extends JpaRepository<PlayerModel, Long> {
    /**
     * Encuentra un PlayerModel por su nombre.
     *
     * @param name el nombre del jugador
     * @return el PlayerModel con el nombre dado, o null si no se encuentra
     */
    PlayerModel findByName(String name);

    /**
     * Encuentra una lista de PlayerModel por el nombre del equipo.
     *
     * @param teamName el nombre del equipo
     * @return una lista de PlayerModel que pertenecen al equipo con el nombre dado
     */
    List<PlayerModel> findByTeamName(String teamName);
}
