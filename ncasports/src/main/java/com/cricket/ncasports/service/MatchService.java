package com.cricket.ncasports.service;

import com.cricket.ncasports.dto.MatchDTO;
import com.cricket.ncasports.dto.PlayerDTO;
import com.cricket.ncasports.model.Match;
import com.cricket.ncasports.model.Player;
import com.cricket.ncasports.model.Role;
import com.cricket.ncasports.model.Tournament;
import com.cricket.ncasports.repository.MatchRepository;
import com.cricket.ncasports.repository.PlayerRepository;
import com.cricket.ncasports.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final TournamentRepository tournamentRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, PlayerRepository playerRepository,
                        TournamentRepository tournamentRepository) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.tournamentRepository = tournamentRepository;
    }

    // Get all matches as MatchDTO
    public List<MatchDTO> getAllMatchesAsDTO() {
        return matchRepository.findAll().stream()
                .map(this::convertToMatchDTO)
                .collect(Collectors.toList());
    }

    // Get match by ID
    public Optional<MatchDTO> getMatchById(Long id) {
        Optional<Match> match = matchRepository.findById(id);
        return match.map(this::convertToMatchDTO);
    }

    public MatchDTO createMatch(MatchDTO matchDTO) {
        // Fetch the tournament by ID
        Tournament tournament = tournamentRepository.findById(matchDTO.getTournamentId())
                .orElseThrow(() -> new IllegalArgumentException("Tournament not found with ID: " + matchDTO.getTournamentId()));

        // Create the Match object
        Match match = new Match();
        match.setTeamA(matchDTO.getTeamA());
        match.setTeamB(matchDTO.getTeamB());
        match.setScheduledDate(matchDTO.getScheduledDate());
        match.setTossWinner(matchDTO.getTossWinner());
        match.setTossDecision(matchDTO.getTossDecision());
        match.setTournament(tournament); // Set the tournament correctly

        // Process team players for team A and B
        List<Player> teamAPlayers = matchDTO.getTeamAPlayers().stream()
                .map(playerDTO -> new Player(playerDTO.getPlayerName(), playerDTO.getRole())) 
                .collect(Collectors.toList());

        List<Player> teamBPlayers = matchDTO.getTeamBPlayers().stream()
                .map(playerDTO -> new Player(playerDTO.getPlayerName(), playerDTO.getRole())) 
                .collect(Collectors.toList());

        // Set the players to the match
        match.setTeamAPlayers(teamAPlayers);
        match.setTeamBPlayers(teamBPlayers);

        // Save the match
        match = matchRepository.save(match);

        return convertToMatchDTO(match);
    }

    private Player createPlayer(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setName(playerDTO.getPlayerName());
        player.setRole(playerDTO.getRole());
        return playerRepository.save(player);
    }

    public MatchDTO convertToMatchDTO(Match match) {
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(match.getId());
        matchDTO.setTeamA(match.getTeamA());
        matchDTO.setTeamB(match.getTeamB());
        matchDTO.setScheduledDate(match.getScheduledDate());
        matchDTO.setTossWinner(match.getTossWinner());
        matchDTO.setTossDecision(match.getTossDecision());
        matchDTO.setTournamentId(match.getTournament().getId());
        matchDTO.setTournamentName(match.getTournament().getName());

        // Map players to DTO
        List<PlayerDTO> teamAPlayers = match.getTeamAPlayers().stream()
                .map(player -> new PlayerDTO(player.getName(), player.getRole()))
                .collect(Collectors.toList());

        List<PlayerDTO> teamBPlayers = match.getTeamBPlayers().stream()
                .map(player -> new PlayerDTO(player.getName(), player.getRole()))
                .collect(Collectors.toList());

        matchDTO.setTeamAPlayers(teamAPlayers);
        matchDTO.setTeamBPlayers(teamBPlayers);

        return matchDTO;
    }
    
    public MatchDTO updateMatch(Long id, MatchDTO matchDTO) {
        // Find the existing match
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));

        // Update match details
        match.setTeamA(matchDTO.getTeamA());
        match.setTeamB(matchDTO.getTeamB());
        match.setScheduledDate(matchDTO.getScheduledDate());
        match.setTossWinner(matchDTO.getTossWinner());
        match.setTossDecision(matchDTO.getTossDecision());

        // Update players for team A
        List<Player> teamAPlayers = matchDTO.getTeamAPlayers().stream()
                .map(playerDTO -> {
                    Player player = playerRepository.findByName(playerDTO.getPlayerName())
                            .orElseGet(() -> {
                                Player newPlayer = new Player();
                                newPlayer.setName(playerDTO.getPlayerName());
                                newPlayer.setRole(playerDTO.getRole()); // Ensure you set the role from the DTO
                                return playerRepository.save(newPlayer);
                            });
                    return player;
                })
                .collect(Collectors.toList());
        match.setTeamAPlayers(teamAPlayers);

        // Update players for team B
        List<Player> teamBPlayers = matchDTO.getTeamBPlayers().stream()
                .map(playerDTO -> {
                    Player player = playerRepository.findByName(playerDTO.getPlayerName())
                            .orElseGet(() -> {
                                Player newPlayer = new Player();
                                newPlayer.setName(playerDTO.getPlayerName());
                                newPlayer.setRole(playerDTO.getRole()); // Ensure you set the role from the DTO
                                return playerRepository.save(newPlayer);
                            });
                    return player;
                })
                .collect(Collectors.toList());
        match.setTeamBPlayers(teamBPlayers);

        // Save the updated match
        match = matchRepository.save(match);

        return convertToMatchDTO(match);
    }

	public void deleteMatch(Long id) {
		Match match = matchRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Match not found"));

	    // Delete the match
	    matchRepository.delete(match);
		
	}

	
	
}
