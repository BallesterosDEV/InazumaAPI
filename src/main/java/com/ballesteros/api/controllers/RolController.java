package com.ballesteros.api.controllers;

import com.ballesteros.api.persistence.models.RolModel;
import com.ballesteros.api.services.RolService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para manejar las solicitudes relacionadas con los roles.
 */
@RestController
@RequestMapping("/api/roles")
@CrossOrigin
public class RolController {

    @Autowired
    RolService rolService;

    /**
     * Inserta un nuevo rol.
     *
     * @param rol el modelo del rol
     */
    @Hidden
    @PostMapping("/insert")
    void insertRol(@RequestBody RolModel rol) {
        rolService.saveRol(rol);
    }
}
