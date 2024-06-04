package com.ballesteros.api.persistence.repositories;

import com.ballesteros.api.persistence.models.TeamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio para entidades TeamModel.
 */
@Repository
public interface TeamRepository extends JpaRepository<TeamModel, Long> {
    /**
     * Encuentra un TeamModel por su nombre.
     *
     * @param name el nombre del equipo
     * @return un Optional que contiene el TeamModel con el nombre dado, o vacío si no se encuentra
     */
    Optional<TeamModel> findByName(String name);
}
