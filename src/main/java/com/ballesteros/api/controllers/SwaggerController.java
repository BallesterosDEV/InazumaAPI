package com.ballesteros.api.controllers;

import com.ballesteros.api.persistence.models.PlayerModel;
import com.ballesteros.api.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para manejar las solicitudes Swagger relacionadas con los jugadores.
 */
@RestController
public class SwaggerController {
    @Autowired
    private PlayerService playerService;

    /**
     * Obtiene todos los jugadores.
     *
     * @return una lista de todos los jugadores
     */
    @GetMapping("/swagger/get-players")
    public ResponseEntity<List<PlayerModel>> getAllPlayers() {
        List<PlayerModel> players = playerService.getAllPlayers("name");
        return ResponseEntity.ok(players);
    }

    /**
     * Obtiene un jugador por su ID.
     *
     * @param id el ID del jugador
     * @return el jugador correspondiente al ID o una respuesta 404 si no se encuentra
     */
    @GetMapping("/swagger/get-player-by-id/{id}")
    public ResponseEntity<PlayerModel> getPlayerById(@PathVariable Long id) {
        PlayerModel player = playerService.getPlayerById(id);
        if (player != null) {
            return ResponseEntity.ok(player);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene un jugador por su nombre.
     *
     * @param name el nombre del jugador
     * @return el jugador correspondiente al nombre o una respuesta 404 si no se encuentra
     */
    @GetMapping("/swagger/get-player-by-name/{name}")
    public ResponseEntity<PlayerModel> getPlayerByName(@PathVariable String name) {
        PlayerModel player = playerService.getPlayerByName(name);
        if (player != null) {
            return ResponseEntity.ok(player);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo jugador.
     *
     * @param player el modelo del jugador a crear
     * @return el jugador creado
     */
    @PostMapping("/swagger/create-player")
    public ResponseEntity<PlayerModel> savePlayer(@RequestBody PlayerModel player) {
        PlayerModel savedPlayer = playerService.savePlayerSwagger(player);
        return ResponseEntity.ok(savedPlayer);
    }

    /**
     * Actualiza un jugador existente.
     *
     * @param id     el ID del jugador a actualizar
     * @param player el modelo del jugador actualizado
     * @return el jugador actualizado
     */
    @PutMapping("/swagger/edit-player/{id}")
    public ResponseEntity<PlayerModel> updatePlayer(@PathVariable Long id, @RequestBody PlayerModel player) {
        PlayerModel updatedPlayer = playerService.updatePlayerSwagger(id, player);
        return ResponseEntity.ok(updatedPlayer);
    }

    /**
     * Elimina un jugador por su ID.
     *
     * @param id el ID del jugador a eliminar
     * @return una respuesta sin contenido
     */
    @DeleteMapping("/swagger/delete-player/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayerSwagger(id);
        return ResponseEntity.noContent().build();
    }
}
