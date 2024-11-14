package com.cricket.ncasports.service;

import com.cricket.ncasports.dto.PlayerDTO;
import com.cricket.ncasports.model.Player;
import com.cricket.ncasports.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<PlayerDTO> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(this::convertToPlayerDTO)
                .collect(Collectors.toList());
    }

    public PlayerDTO getPlayerById(Long id) {
        return playerRepository.findById(id)
                .map(this::convertToPlayerDTO)
                .orElse(null);
    }

    public PlayerDTO createPlayer(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setName(playerDTO.getPlayerName());
        player.setRole(playerDTO.getRole());
        player = playerRepository.save(player);
        return convertToPlayerDTO(player);
    }

    public PlayerDTO updatePlayer(Long id, PlayerDTO playerDTO) {
        return playerRepository.findById(id)
                .map(player -> {
                    player.setName(playerDTO.getPlayerName());
                    player.setRole(playerDTO.getRole());
                    player = playerRepository.save(player);
                    return convertToPlayerDTO(player);
                })
                .orElse(null);
    }

    public boolean deletePlayer(Long id) {
        if (playerRepository.existsById(id)) {
            playerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PlayerDTO convertToPlayerDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setPlayerName(player.getName());
        playerDTO.setRole(player.getRole());
        return playerDTO;
    }
}
