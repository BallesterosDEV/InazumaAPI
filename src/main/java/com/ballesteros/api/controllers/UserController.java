package com.ballesteros.api.controllers;

import com.ballesteros.api.persistence.models.PlayerModel;
import com.ballesteros.api.persistence.models.UserModel;
import com.ballesteros.api.security.auth.service.AuthService;
import com.ballesteros.api.services.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

/**
 * Controlador para manejar las solicitudes relacionadas con los usuarios.
 */
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private PlayerService playerService;

    /**
     * Añade un jugador a la colección del usuario.
     *
     * @param playerId el ID del jugador
     * @param principal la información del usuario autenticado
     * @param model el modelo
     * @return la URL de redirección
     */
    @PostMapping("/inazuma-stars/add")
    public String addPlayerToStars(@RequestParam Long playerId, Principal principal, Model model) {
        logger.info("Añadiendo jugador a la colección del usuario");
        UserModel currentUser = authService.findByUsername(principal.getName());
        PlayerModel player = playerService.getPlayerById(playerId);

        if (player != null) {
            if (currentUser.getPlayers().contains(player)) {
                model.addAttribute("errorMessage", "Player already in your Inazuma Stars");
                return "redirect:/inazuma-stars";
            }

            long count = currentUser.getPlayers().stream()
                    .filter(p -> p.getPosition() == player.getPosition())
                    .count();
            if (count >= 5) {
                model.addAttribute("errorMessage", "The maximum is 5 for each position");
                return "redirect:/inazuma-stars";
            }

            currentUser.getPlayers().add(player);
            authService.save(currentUser);
        }

        return "redirect:/inazuma-stars";
    }

    /**
     * Elimina un jugador de la colección del usuario.
     *
     * @param playerId el ID del jugador
     * @param principal la información del usuario autenticado
     * @param model el modelo
     * @return la URL de redirección
     */
    @PostMapping("/inazuma-stars/remove")
    public String removePlayerFromStars(@RequestParam Long playerId, Principal principal, Model model) {
        logger.info("Eliminando jugador de la colección del usuario");
        UserModel currentUser = authService.findByUsername(principal.getName());
        PlayerModel player = playerService.getPlayerById(playerId);

        if (player != null) {
            currentUser.getPlayers().remove(player);
            authService.save(currentUser);
        }

        return "redirect:/inazuma-stars";
    }


}
