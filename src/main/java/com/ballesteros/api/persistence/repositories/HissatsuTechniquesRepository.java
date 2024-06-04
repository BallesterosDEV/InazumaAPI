package com.ballesteros.api.persistence.repositories;

import com.ballesteros.api.persistence.models.HissatsuTechniquesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para entidades HissatsuTechniquesModel.
 */
@Repository
public interface HissatsuTechniquesRepository extends JpaRepository<HissatsuTechniquesModel, Long> {
    /**
     * Encuentra un HissatsuTechniquesModel por su nombre.
     *
     * @param name el nombre de la técnica
     * @return el HissatsuTechniquesModel con el nombre dado, o null si no se encuentra
     */
    HissatsuTechniquesModel findByName(String name);
}
