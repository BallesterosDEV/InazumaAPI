package com.ballesteros.api.services;

import com.ballesteros.api.persistence.models.TeamModel;
import com.ballesteros.api.persistence.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Servicio para gestionar TeamModel.
 */
@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    /**
     * Guarda un equipo.
     *
     * @param team el modelo del equipo a guardar
     */
    public TeamModel saveTeam(TeamModel team, MultipartFile image) {

        if (!image.isEmpty()) {
            String absolutePath = "C://TFG//resources";
            try {
                byte[] bytesImg = image.getBytes();
                Path path = Paths.get(absolutePath + "//" + image.getOriginalFilename());
                Files.write(path, bytesImg);

                team.setImage(image.getOriginalFilename());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return teamRepository.save(team);
    }

    /**
     * Obtiene todos los equipos.
     *
     * @return una lista de todos los equipos
     */
    public List<TeamModel> getAllTeams() {
        return teamRepository.findAll();
    }


}
