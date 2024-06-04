package com.ballesteros.api.persistence.repositories;

import com.ballesteros.api.persistence.models.RolModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para entidades RolModel.
 */
@Repository
public interface RolRepository extends JpaRepository<RolModel, Long> {
}
