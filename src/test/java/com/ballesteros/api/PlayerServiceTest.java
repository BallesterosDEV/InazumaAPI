package com.ballesteros.api;

import com.ballesteros.api.enums.Element;
import com.ballesteros.api.enums.PlayerPosition;
import com.ballesteros.api.persistence.models.HissatsuTechniquesModel;
import com.ballesteros.api.persistence.models.PlayerModel;
import com.ballesteros.api.persistence.models.TeamModel;
import com.ballesteros.api.persistence.repositories.PlayerRepository;
import com.ballesteros.api.services.HissatsuTechniquesService;
import com.ballesteros.api.services.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private HissatsuTechniquesService hissatsuTechniquesService;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPlayers() {
        // Arrange
        PlayerModel player1 = new PlayerModel();
        player1.setName("Player1");
        PlayerModel player2 = new PlayerModel();
        player2.setName("Player2");

        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));

        // Act
        List<PlayerModel> players = playerService.getAllPlayers("name");

        // Assert
        assertNotNull(players);
        assertEquals(2, players.size());
        assertEquals("Player1", players.get(0).getName());
        assertEquals("Player2", players.get(1).getName());
        verify(playerRepository, times(1)).findAll();
    }

    @Test
    void testGetPlayerById() {
        // Arrange
        PlayerModel player = new PlayerModel();
        player.setId(1L);
        player.setName("Player1");

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        // Act
        PlayerModel foundPlayer = playerService.getPlayerById(1L);

        // Assert
        assertNotNull(foundPlayer);
        assertEquals("Player1", foundPlayer.getName());
        verify(playerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPlayerByName() {
        // Arrange
        PlayerModel player = new PlayerModel();
        player.setName("Player1");

        when(playerRepository.findByName("Player1")).thenReturn(player);

        // Act
        PlayerModel foundPlayer = playerService.getPlayerByName("Player1");

        // Assert
        assertNotNull(foundPlayer);
        assertEquals("Player1", foundPlayer.getName());
        verify(playerRepository, times(1)).findByName("Player1");
    }

    @Test
    void testSearchPlayersByTeamName() {
        // Arrange
        TeamModel team = new TeamModel();
        team.setName("Team1");
        PlayerModel player1 = new PlayerModel();
        player1.setName("Player1");
        player1.setTeam(team);
        PlayerModel player2 = new PlayerModel();
        player2.setName("Player2");
        player2.setTeam(team);

        when(playerRepository.findByTeamName("Team1")).thenReturn(Arrays.asList(player1, player2));

        // Act
        List<PlayerModel> players = playerService.searchPlayersByTeamName("Team1");

        // Assert
        assertNotNull(players);
        assertEquals(2, players.size());
        assertEquals("Player1", players.get(0).getName());
        assertEquals("Player2", players.get(1).getName());
        verify(playerRepository, times(1)).findByTeamName("Team1");
    }

    @Test
    void testSavePlayer() {
        // Arrange
        TeamModel team = new TeamModel();
        HissatsuTechniquesModel technique = new HissatsuTechniquesModel();
        technique.setName("Technique1");
        List<HissatsuTechniquesModel> list = new ArrayList<>();
        list.add(technique);
        team.setName("Team1");
        PlayerModel player = new PlayerModel();
        player.setName("Player1");
        player.setNickname("Player1 nickname");
        player.setPosition(PlayerPosition.FW);
        player.setElement(Element.FIRE);
        player.setTeam(team);
        player.setHissatsuTechniques(list);
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[0]);

        when(playerRepository.save(any(PlayerModel.class))).thenReturn(player);
        when(hissatsuTechniquesService.getTechniqueByName("Technique1")).thenReturn(technique);

        // Act
        PlayerModel savedPlayer = playerService.savePlayer(player, image);

        // Assert
        assertNotNull(savedPlayer);
        assertEquals("Player1", savedPlayer.getName());
        assertEquals("Team1", savedPlayer.getTeam().getName());
        verify(playerRepository, times(1)).save(any(PlayerModel.class));
    }

    @Test
    void testUpdatePlayer() {
        // Arrange
        PlayerModel existingPlayer = new PlayerModel();
        existingPlayer.setId(1L);
        existingPlayer.setName("Existing Player");

        PlayerModel updatedPlayer = new PlayerModel();
        updatedPlayer.setName("Updated Player");

        when(playerRepository.findById(1L)).thenReturn(Optional.of(existingPlayer));

        // Act
        playerService.updatePlayer(1L, updatedPlayer, null);

        // Assert
        assertEquals("Updated Player", existingPlayer.getName());
        verify(playerRepository, times(1)).save(existingPlayer);
    }

    @Test
    void testDeletePlayer() {
        // Arrange
        PlayerModel player = new PlayerModel();
        player.setId(1L);
        player.setName("Player1");
        player.setImage("image.jpg");

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        // Act
        playerService.deletePlayer(1L);

        // Assert
        verify(playerRepository, times(1)).delete(player);
    }
}