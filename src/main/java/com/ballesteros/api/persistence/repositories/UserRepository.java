package com.ballesteros.api.persistence.repositories;

import com.ballesteros.api.persistence.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para entidades UserModel.
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    /**
     * Encuentra un UserModel por su nombre.
     *
     * @param username el nombre del usuario
     * @return el UserModel con el nombre dado, o null si no se encuentra
     */
    Optional<UserModel> findByUsername(String username);

    /**
     * Encuentra un UserModel por su email.
     *
     * @param email el email del usuario
     * @return el UserModel con el email dado, o null si no se encuentra
     */
    Optional<UserModel> findByEmail(String email);
}
