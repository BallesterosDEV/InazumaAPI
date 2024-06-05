package com.ballesteros.api.services;

import com.ballesteros.api.enums.Element;
import com.ballesteros.api.enums.HissatsuTechniquesType;
import com.ballesteros.api.persistence.models.HissatsuTechniquesModel;
import com.ballesteros.api.persistence.models.PlayerModel;
import com.ballesteros.api.persistence.models.TeamModel;
import com.ballesteros.api.persistence.repositories.PlayerRepository;
import com.ballesteros.api.persistence.repositories.TeamRepository;
import com.ballesteros.api.utils.ElementComparator;
import com.ballesteros.api.utils.InvalidFileTypeException;
import com.ballesteros.api.utils.PositionComparator;
import com.ballesteros.api.utils.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Servicio para gestionar PlayerModel.
 */
@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private HissatsuTechniquesService hissatsuTechniquesService;

    /**
     * Obtiene todos los jugadores, opcionalmente ordenados por un campo especificado.
     *
     * @param sort el campo por el que ordenar (name, position, element, team)
     * @return una lista de todos los jugadores, ordenados según se especifique
     */
    public List<PlayerModel> getAllPlayers(String sort) {
        List<PlayerModel> players = playerRepository.findAll();
        switch (sort != null ? sort.toLowerCase() : "") {
            case "name":
                Collections.sort(players, Comparator.comparing(PlayerModel::getName));
                break;
            case "position":
                players.sort(new PositionComparator());
                break;
            case "element":
                players.sort(new ElementComparator());
                break;
            case "team":
                Collections.sort(players, Comparator.comparing(player -> player.getTeam().getName()));
                break;
            default:
                break;
        }
        return players;
    }

    /**
     * Obtiene un jugador por su ID.
     *
     * @param id el ID del jugador
     * @return el PlayerModel con el ID dado, o null si no se encuentra
     */
    public PlayerModel getPlayerById(Long id) {
        Optional<PlayerModel> playerOptional = playerRepository.findById(id);
        return playerOptional.orElse(null);
    }

    /**
     * Obtiene un jugador por su nombre.
     *
     * @param name el nombre del jugador
     * @return el PlayerModel con el nombre dado, o null si no se encuentra
     */
    public PlayerModel getPlayerByName(String name) {
        return playerRepository.findByName(name);
    }

    /**
     * Busca jugadores por el nombre del equipo.
     *
     * @param teamName el nombre del equipo
     * @return una lista de PlayerModel que pertenecen al equipo con el nombre dado
     */
    public List<PlayerModel> searchPlayersByTeamName(String teamName) {
        return playerRepository.findByTeamName(teamName);
    }

    /**
     * Guarda un jugador, opcionalmente con una imagen.
     *
     * @param player el modelo del jugador a guardar
     * @param image  el archivo de imagen del jugador
     * @return el PlayerModel guardado
     */
    public PlayerModel savePlayer(PlayerModel player, MultipartFile image) {

        if (image != null && !image.isEmpty()) {
            Validations validations = new Validations();
            String originalFilename = image.getOriginalFilename();
            if (!validations.isValidImage(originalFilename)) {
                throw new InvalidFileTypeException(" Only .png, .jpeg, .jpg, and .webp files are allowed.");
            }

            String absolutePath = "C://TFG//resources";
            try {
                byte[] bytesImg = image.getBytes();
                Path path = Paths.get(absolutePath + "//" + originalFilename);
                Files.write(path, bytesImg);

                player.setImage(originalFilename);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Si no hay imagen proporcionada, establece una imagen predeterminada
            player.setImage("default-image.jpg");
        }

        generateStats(player);

        if (player.getNickname() == null || player.getNickname().isBlank()) {
            player.setNickname("-");
        }

        HissatsuTechniquesModel cargaPotente = hissatsuTechniquesService.getTechniqueByName("Carga Potente");
        HissatsuTechniquesModel bloqueoPotente = hissatsuTechniquesService.getTechniqueByName("Bloqueo Potente");

        if (cargaPotente != null && bloqueoPotente != null) {
            if (player.getHissatsuTechniques() == null) {
                player.setHissatsuTechniques(new ArrayList<>());
                player.getHissatsuTechniques().add(cargaPotente);
                player.getHissatsuTechniques().add(bloqueoPotente);
            }
        }


        return playerRepository.save(player);
    }

    /**
     * Actualiza un jugador con nueva información.
     *
     * @param playerId el ID del jugador a actualizar
     * @param updatedPlayer el modelo del jugador actualizado
     * @param file el archivo de imagen del jugador
     */
    public void updatePlayer(Long playerId, PlayerModel updatedPlayer, MultipartFile file) {
        PlayerModel player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));

        player.setName(updatedPlayer.getName());
        player.setNickname(updatedPlayer.getNickname());
        player.setPosition(updatedPlayer.getPosition());
        player.setTeam(updatedPlayer.getTeam());

        if (file != null && !file.isEmpty()) {
            // Validar el tipo de archivo
            Validations validations = new Validations();
            String originalFilename = file.getOriginalFilename();
            if (!validations.isValidImage(originalFilename)) {
                throw new InvalidFileTypeException("Only .png, .jpeg, .jpg, and .webp files are allowed.");
            }

            if (!player.getImage().equalsIgnoreCase("default-image.jpg")) {
                deleteFile(player.getImage());
            }

            String imageUrl = saveFile(file);
            player.setImage(imageUrl);
        }

        if (player.getNickname() == null || player.getNickname().isBlank()) {
            player.setNickname("-");
        }

        playerRepository.save(player);
    }

    /**
     * Elimina un jugador por su ID.
     *
     * @param playerId el ID del jugador a eliminar
     */
    public void deletePlayer(Long playerId) {
        PlayerModel player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        // Eliminar la imagen
        if (!player.getImage().equalsIgnoreCase("default-image.jpg")) {
            deleteFile(player.getImage());
        }

        // Eliminar el jugador
        playerRepository.delete(player);
    }

    /**
     * Guarda un archivo de imagen.
     *
     * @param file el archivo de imagen a guardar
     * @return el nombre del archivo guardado
     */
    private String saveFile(MultipartFile file) {
        if (!file.isEmpty()) {
            String absolutePath = "C://TFG//resources";
            try {
                byte[] bytesImg = file.getBytes();
                Path path = Paths.get(absolutePath + "//" + file.getOriginalFilename());
                Files.write(path, bytesImg);
                return file.getOriginalFilename();
            } catch (IOException e) {
                throw new RuntimeException("Failed to save file", e);
            }
        }
        return null;
    }

    /**
     * Elimina un archivo de imagen.
     *
     * @param filename el nombre del archivo a eliminar
     */
    private void deleteFile(String filename) {
        if (filename != null && !filename.isEmpty()) {
            String absolutePath = "C://TFG//resources";
            Path path = Paths.get(absolutePath + "//" + filename);
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete file: " + filename, e);
            }
        }
    }

    /**
     * Genera estadísticas para un jugador.
     *
     * @param player el modelo del jugador
     */
    private void generateStats(PlayerModel player) {

        int range = 100 - 25 + 1;
        player.setTechnique((int) (Math.random() * range) + 25);
        player.setKick((int) (Math.random() * range) + 25);
        player.setControl((int) (Math.random() * range) + 25);
        player.setPressure((int) (Math.random() * range) + 25);
        player.setAgility((int) (Math.random() * range) + 25);
        player.setPhysical((int) (Math.random() * range) + 25);
        player.setIntelligence((int) (Math.random() * range) + 25);

        range = 100 - 75 + 1;

        switch (player.getPosition()) {
            case FW:
                player.setKick((int) (Math.random() * range) + 75);
                player.setControl((int) (Math.random() * range) + 75);
                break;
            case MF:
                player.setTechnique((int) (Math.random() * range) + 75);
                player.setControl((int) (Math.random() * range) + 75);
                player.setIntelligence((int) (Math.random() * range) + 75);
                break;
            case DF:
                player.setPressure((int) (Math.random() * range) + 75);
                player.setPhysical((int) (Math.random() * range) + 75);
                player.setIntelligence((int) (Math.random() * range) + 75);
                break;
            case GK:
                player.setAgility((int) (Math.random() * range) + 75);
                player.setPhysical((int) (Math.random() * range) + 75);
                break;
            default:
                break;
        }

    }

    /**
     * Compara dos jugadores en modo versus.
     *
     * @param player1 el primer jugador
     * @param player2 el segundo jugador
     * @param technique1 la técnica del primer jugador
     * @param technique2 la técnica del segundo jugador
     * @return el jugador ganador o un nuevo PlayerModel si hay empate
     */
    public PlayerModel versusMode(PlayerModel player1, PlayerModel player2, HissatsuTechniquesModel technique1, HissatsuTechniquesModel technique2) {
        int powerPlayer1 = calculatePower(player1, technique1);
        int powerPlayer2 = calculatePower(player2, technique2);

        // Aplicar el multiplicador de elemento
        if (isElementStrongAgainst(player1.getElement(), player2.getElement())) {
            powerPlayer1 *= 1.5;
        } else if (isElementStrongAgainst(player2.getElement(), player1.getElement())) {
            powerPlayer2 *= 1.5;
        }

        // Determinar el ganador
        if (powerPlayer1 > powerPlayer2) {
            return player1;
        } else if (powerPlayer2 > powerPlayer1) {
            return player2;
        } else {
            return new PlayerModel();
        }
    }

    /**
     * Calcula el poder de un jugador usando una técnica Hissatsu.
     *
     * @param player el jugador
     * @param technique la técnica Hissatsu
     * @return el poder calculado
     */
    private int calculatePower(PlayerModel player, HissatsuTechniquesModel technique) {
        int power = 0;

        // Obtener la potencia base según la posición del jugador
        switch (player.getPosition()) {
            case FW:
                power = Math.max(player.getKick(), player.getControl());
                break;
            case MF:
                power = Math.max(Math.max(player.getTechnique(), player.getControl()), player.getIntelligence());
                break;
            case DF:
                power = Math.max(Math.max(player.getPressure(), player.getPhysical()), player.getIntelligence());
                break;
            case GK:
                power = Math.max(player.getAgility(), player.getPhysical());
                break;
        }

        // Añadir la potencia de la supertécnica
        power += technique.getPower();

        // Aplicar el multiplicador si la supertécnica es del tipo correcto para la posición del jugador

        switch (player.getPosition()) {
            case FW:
                if (technique.getType() == HissatsuTechniquesType.SHOOT) {
                    power *= 1.5;
                }
                break;
            case MF:
                if (technique.getType() == HissatsuTechniquesType.DRIBBLE) {
                    power *= 1.5;
                }
                break;
            case DF:
                if (technique.getType() == HissatsuTechniquesType.BLOCK) {
                    power *= 1.5;
                }
                break;
            case GK:
                if (technique.getType() == HissatsuTechniquesType.CATCH) {
                    power *= 1.5;
                }
                break;
        }

        return power;
    }

    /**
     * Determina si un elemento es fuerte contra otro elemento.
     *
     * @param element1 el primer elemento
     * @param element2 el segundo elemento
     * @return true si el primer elemento es fuerte contra el segundo, false en caso contrario
     */
    private boolean isElementStrongAgainst(Element element1, Element element2) {
        switch (element1) {
            case WIND:
                return element2 == Element.EARTH;
            case EARTH:
                return element2 == Element.FIRE;
            case FIRE:
                return element2 == Element.WOOD;
            case WOOD:
                return element2 == Element.WIND;
            default:
                return false;
        }
    }

    /**
     * Guarda un jugador sin imagen usando Swagger.
     *
     * @param player el modelo del jugador a guardar
     * @return el PlayerModel guardado
     */
    public PlayerModel savePlayerSwagger(PlayerModel player) {
        generateStats(player);

        HissatsuTechniquesModel cargaPotente = hissatsuTechniquesService.getTechniqueByName("Carga Potente");
        HissatsuTechniquesModel bloqueoPotente = hissatsuTechniquesService.getTechniqueByName("Bloqueo Potente");



        if (cargaPotente != null && bloqueoPotente != null) {
            if (player.getHissatsuTechniques() == null) {
                player.setHissatsuTechniques(new ArrayList<>());
                player.getHissatsuTechniques().add(cargaPotente);
                player.getHissatsuTechniques().add(bloqueoPotente);
            }
        }

        Optional<TeamModel> team = teamRepository.findByName("Instituto Raimon");
        team.ifPresent(player::setTeam);


        return playerRepository.save(player);
    }

    /**
     * Actualiza un jugador sin imagen usando Swagger.
     *
     * @param updatedPlayer el modelo del jugador a actualizar
     * @return el PlayerModel actualizado
     */
    public PlayerModel updatePlayerSwagger(Long playerId, PlayerModel updatedPlayer) {
        PlayerModel player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));

        player.setName(updatedPlayer.getName());
        player.setNickname(updatedPlayer.getNickname());
        player.setPosition(updatedPlayer.getPosition());
        Optional<TeamModel> team = teamRepository.findByName("Instituto Raimon");
        team.ifPresent(player::setTeam);


        return playerRepository.save(player);
    }

    /**
     * Elimina un jugador sin imagen usando Swagger.
     * @param playerId el id del jugador a eliminar
     */
    public void deletePlayerSwagger(Long playerId) {
        PlayerModel player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        playerRepository.delete(player);
    }


}
