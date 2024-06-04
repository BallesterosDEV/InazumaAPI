package com.ballesteros.api.controllers;

import com.ballesteros.api.persistence.models.HissatsuTechniquesModel;
import com.ballesteros.api.persistence.models.PlayerModel;
import com.ballesteros.api.persistence.models.TeamModel;
import com.ballesteros.api.persistence.models.UserModel;
import com.ballesteros.api.security.auth.modeldto.LoginRequestDTO;
import com.ballesteros.api.security.auth.modeldto.RegisterRequestDTO;
import com.ballesteros.api.security.auth.service.AuthService;
import com.ballesteros.api.services.HissatsuTechniquesService;
import com.ballesteros.api.services.PlayerService;
import com.ballesteros.api.services.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

/**
 * Controlador para manejar las solicitudes relacionadas con la página principal.
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private TeamService teamService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PlayerService playerService;
    @Autowired
    private HissatsuTechniquesService hissatsuTechniquesService;

    /**
     * Maneja las solicitudes a la página de login.
     *
     * @param model el modelo
     * @return la página de login
     */
    @GetMapping("/login")
    public String login(Model model) {
        logger.info("Accediendo a la página de login");
        model.addAttribute("loginRequest", new LoginRequestDTO());
        return "login";
    }

    /**
     * Maneja las solicitudes a la página de registro.
     *
     * @param model el modelo
     * @return la página de registro
     */

    @GetMapping("/register")
    public String register(Model model) {
        logger.info("Accediendo a la página de registro");
        model.addAttribute("registerRequest", new RegisterRequestDTO());
        return "register";
    }

    /**
     * Maneja las solicitudes a la página principal.
     *
     * @param model     el modelo
     * @param principal el principal
     * @return la página principal
     */
    @GetMapping("/")
    public String showIndexPage(Model model, Principal principal) {
        logger.info("Accediendo a la página principal");
        if (principal != null) {
            UserModel currentUser = authService.findByUsername(principal.getName());
            model.addAttribute("userName", currentUser.getUsername());
        }
        return "index"; // Asegúrate de que este nombre coincida con tu plantilla HTML
    }

    /**
     * Maneja las solicitudes a la página de visualización de jugadores.
     *
     * @param sort  el parámetro de ordenación
     * @param model el modelo
     * @return la página de visualización de jugadores
     */
    @GetMapping("/show-players")
    public String showPlayersPage(@RequestParam(value = "sort", required = false) String sort, Model model) {
        logger.info("Accediendo a la página de vista de jugadores");
        List<PlayerModel> playerList = playerService.getAllPlayers(sort);
        model.addAttribute("players", playerList);
        return "show-players";
    }

    /**
     * Maneja las solicitudes a la página de creación de jugadores.
     *
     * @param model el modelo
     * @return la página de creación de jugadores
     */
    @GetMapping("/create-player")
    public String showCreatePlayerForm(Model model) {
        logger.info("Accediendo a la página de creación de jugadores");
        PlayerModel playerModel = new PlayerModel();
        List<TeamModel> teams = teamService.getAllTeams();
        List<HissatsuTechniquesModel> hissatsuTechniques = hissatsuTechniquesService.getAllHissatsuTechniques();
        logger.info("Técnicas Hissatsu cargadas: " + hissatsuTechniques.size()); // Añadir log para verificar el tamaño de la lista
        model.addAttribute("player", playerModel);
        model.addAttribute("teams", teams);
        model.addAttribute("hissatsuTechniques", hissatsuTechniques);
        return "create-player";
    }

    /**
     * Maneja las solicitudes a la página de modo versus.
     *
     * @param model el modelo
     * @return la página de modo versus
     */
    @GetMapping("/versus-mode")
    public String showVersusMode(Model model) {
        logger.info("Accediendo a la página de modo versus");
        List<PlayerModel> players = playerService.getAllPlayers("name");
        model.addAttribute("players", players);
        return "versus-mode";
    }

    /**
     * Maneja las solicitudes a la página de colección de jugadores del usuario.
     *
     * @param model     el modelo
     * @param principal el principal
     * @param error     el mensaje de error
     * @return la página de colección de jugadores del usuario
     */
    @GetMapping("/inazuma-stars")
    public String showInazumaStarsPage(Model model, Principal principal,
                                       @RequestParam(name = "error", required = false) String error) {
        logger.info("Accediendo a la página de vista de colección de jugadores del usuario");
        UserModel currentUser = authService.findByUsername(principal.getName());
        List<PlayerModel> userPlayers = currentUser.getPlayers();
        List<PlayerModel> allPlayers = playerService.getAllPlayers("name");

        model.addAttribute("userPlayers", userPlayers);
        model.addAttribute("allPlayers", allPlayers);
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }

        return "inazuma-stars";
    }
}

