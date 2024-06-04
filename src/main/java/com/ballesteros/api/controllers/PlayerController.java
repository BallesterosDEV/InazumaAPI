package com.ballesteros.api.controllers;

import com.ballesteros.api.persistence.models.HissatsuTechniquesModel;
import com.ballesteros.api.persistence.models.PlayerModel;
import com.ballesteros.api.persistence.models.TeamModel;
import com.ballesteros.api.services.HissatsuTechniquesService;
import com.ballesteros.api.services.PlayerService;
import com.ballesteros.api.services.TeamService;
import com.ballesteros.api.utils.InvalidFileTypeException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para manejar las solicitudes relacionadas con los jugadores.
 */
@Controller
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private HissatsuTechniquesService hissatsuTechniquesService;

    @Autowired
    private HissatsuTechniquesService hissatsuTechniqueService;

    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    /**
     * Guarda un jugador.
     *
     * @param playerModel        el modelo del jugador
     * @param bindingResult      el resultado de la validación
     * @param image              el archivo de imagen
     * @param redirectAttributes los atributos de redirección
     * @param model              el modelo
     * @return la URL de redirección
     */
    @PostMapping("/save-player")
    public String savePlayer(@ModelAttribute("player") @Valid PlayerModel playerModel,
                             BindingResult bindingResult,
                             @RequestParam(value = "file", required = false) MultipartFile image,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        logger.info("Creando jugador");
        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamService.getAllTeams());
            model.addAttribute("hissatsuTechniques", hissatsuTechniquesService.getAllHissatsuTechniques());
            return "create-player";
        }

        try {
            playerService.savePlayer(playerModel, image);
            redirectAttributes.addFlashAttribute("message", "Player saved successfully!");
        } catch (InvalidFileTypeException e) {
            bindingResult.rejectValue("image", "error.player", e.getMessage());
            model.addAttribute("teams", teamService.getAllTeams());
            model.addAttribute("hissatsuTechniques", hissatsuTechniquesService.getAllHissatsuTechniques());
            return "create-player";
        }

        return "redirect:/show-players";
    }

    /**
     * Muestra el formulario de búsqueda.
     *
     * @param model el modelo
     * @return la página del formulario de búsqueda
     */
    @GetMapping("/search")
    public String showSearchForm(Model model) {
        logger.info("Mostrando el formulario de búsquedas");
        model.addAttribute("player", null);
        model.addAttribute("players", null);
        model.addAttribute("searchedPlayer", false);
        model.addAttribute("searchedTeam", false);
        return "search";
    }

    /**
     * Busca un jugador por nombre.
     *
     * @param name  el nombre del jugador
     * @param model el modelo
     * @return la página de resultados de búsqueda
     */
    @GetMapping("/search-player-result")
    public String searchPlayerByName(@RequestParam("name") String name, Model model) {
        logger.info("Mostrando el resultado de la búsqueda de jugador por nombre");
        PlayerModel player = playerService.getPlayerByName(name);
        model.addAttribute("player", player);
        model.addAttribute("searchedPlayer", true);
        model.addAttribute("searchedTeam", false);
        return "search";
    }

    /**
     * Busca jugadores por nombre del equipo.
     *
     * @param team  el nombre del equipo
     * @param model el modelo
     * @return la página de resultados de búsqueda
     */
    @GetMapping("/search-team-players-result")
    public String searchTeamPlayersResult(@RequestParam("team") String team, Model model) {
        logger.info("Mostrando el resultado de la búsqueda de jugadores por equipo");
        List<PlayerModel> players = playerService.searchPlayersByTeamName(team);
        model.addAttribute("players", players != null ? players : new ArrayList<>());
        model.addAttribute("searchedTeam", true);
        model.addAttribute("searchedPlayer", false);
        return "search";
    }

    /**
     * Busca técnicas hissatsu por nombre.
     *
     * @param techniqueName el nombre de la técnica
     * @param model         el modelo
     * @return la página de resultados de búsqueda
     */
    @GetMapping("/search-technique-result")
    public String searchTechniqueByName(@RequestParam("techniqueName") String techniqueName, Model model) {
        logger.info("Mostrando el resultado de la búsqueda de supertécnicas por nombre");
        HissatsuTechniquesModel technique = hissatsuTechniqueService.getTechniqueByName(techniqueName);
        model.addAttribute("technique", technique);
        model.addAttribute("searchedTechnique", true);
        model.addAttribute("searchedPlayer", false);
        model.addAttribute("searchedTeam", false);
        return "search";
    }

    /**
     * Muestra los detalles del jugador.
     *
     * @param playerId el ID del jugador
     * @param model    el modelo
     * @return la página de detalles del jugador
     */
    @GetMapping("player-details/{id}")
    public String playerDetails(@PathVariable("id") Long playerId, Model model) {
        logger.info("Mostrando los detalles del jugador");
        PlayerModel player = playerService.getPlayerById(playerId);
        model.addAttribute("player", player);
        return "player-details";
    }

    /**
     * Muestra el formulario de edición de un jugador.
     *
     * @param playerId el ID del jugador
     * @param model    el modelo
     * @return la página del formulario de edición del jugador
     */
    @GetMapping("/edit-player/{id}")
    public String showEditPlayerForm(@PathVariable("id") Long playerId, Model model) {
        logger.info("Mostrando el formulario de edición de un jugador");
        PlayerModel player = playerService.getPlayerById(playerId);
        List<TeamModel> teams = teamService.getAllTeams();
        model.addAttribute("player", player);
        model.addAttribute("teams", teams);
        return "edit-player";
    }

    /**
     * Actualiza un jugador.
     *
     * @param playerId           el ID del jugador
     * @param player             el modelo del jugador
     * @param bindingResult      el resultado de la validación
     * @param file               el archivo de imagen
     * @param model              el modelo
     * @param redirectAttributes los atributos de redirección
     * @return la URL de redirección
     */
    @PostMapping("/update-player/{id}")
    public String updatePlayer(@PathVariable("id") Long playerId,
                               @ModelAttribute("player") @Valid PlayerModel player,
                               BindingResult bindingResult,
                               @RequestParam("file") MultipartFile file,
                               Model model, RedirectAttributes redirectAttributes) {
        logger.info("Actualizando jugador");
        if (bindingResult.hasErrors()) {
            List<TeamModel> teams = teamService.getAllTeams();
            model.addAttribute("teams", teams);
            return "edit-player";
        }

        try {
            playerService.updatePlayer(playerId, player, file);
            redirectAttributes.addFlashAttribute("message", "Player updated successfully!");
        } catch (InvalidFileTypeException e) {
            bindingResult.rejectValue("image", "error.player", e.getMessage());
            List<TeamModel> teams = teamService.getAllTeams();
            model.addAttribute("teams", teams);
            return "edit-player";
        }

        return "redirect:/show-players";
    }

    /**
     * Elimina un jugador.
     *
     * @param playerId           el ID del jugador
     * @param redirectAttributes los atributos de redirección
     * @return la URL de redirección
     */
    @GetMapping("/delete-player/{id}")
    public String deletePlayer(@PathVariable("id") Long playerId, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando Jugador");
        playerService.deletePlayer(playerId);
        redirectAttributes.addFlashAttribute("message", "Player deleted successfully!");
        return "redirect:/show-players";
    }

    /**
     * Obtiene un jugador por su ID.
     *
     * @param id el ID del jugador
     * @return el jugador o una respuesta 404 si no se encuentra
     */
    @GetMapping("/players/{id}")
    public ResponseEntity<PlayerModel> getPlayerById(@PathVariable Long id) {
        logger.info("Buscando jugador por id");
        PlayerModel player = playerService.getPlayerById(id);
        if (player != null) {
            return ResponseEntity.ok(player);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Maneja el modo versus entre dos jugadores.
     *
     * @param player1Id    el ID del primer jugador
     * @param technique1Id el ID de la técnica del primer jugador
     * @param player2Id    el ID del segundo jugador
     * @param technique2Id el ID de la técnica del segundo jugador
     * @return el jugador ganador o un empate
     */
    @PostMapping("/players/versus")
    public ResponseEntity<PlayerModel> versus(@RequestParam Long player1Id, @RequestParam Long technique1Id, @RequestParam Long player2Id, @RequestParam Long technique2Id) {
        logger.info("Mostrando resultado del versus");
        PlayerModel player1 = playerService.getPlayerById(player1Id);
        PlayerModel player2 = playerService.getPlayerById(player2Id);
        HissatsuTechniquesModel technique1 = hissatsuTechniqueService.getTechniqueById(technique1Id);
        HissatsuTechniquesModel technique2 = hissatsuTechniqueService.getTechniqueById(technique2Id);

        PlayerModel winner = playerService.versusMode(player1, player2, technique1, technique2);
        if (winner.getId() == null) { // Empate
            return ResponseEntity.ok(new PlayerModel() {{
                setName("¡Draw!");
            }});
        }
        return ResponseEntity.ok(winner);
    }

}