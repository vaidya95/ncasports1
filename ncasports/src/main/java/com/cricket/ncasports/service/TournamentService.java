package com.cricket.ncasports.service;

import com.cricket.ncasports.dto.MatchDTO;
import com.cricket.ncasports.dto.TournamentDTO;
import com.cricket.ncasports.model.Tournament;
import com.cricket.ncasports.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final MatchService matchService;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository, MatchService matchService) {
        this.tournamentRepository = tournamentRepository;
        this.matchService = matchService;
    }

    // Method to get all tournaments as DTOs
    public List<TournamentDTO> getAllTournamentsAsDTO() {
        return tournamentRepository.findAll().stream()
                .map(this::convertToTournamentDTO)
                .collect(Collectors.toList());
    }

    // Method to get a single tournament by ID as DTO
    public Optional<TournamentDTO> getTournamentById(Long id) {
        Optional<Tournament> tournament = tournamentRepository.findById(id);
        return tournament.map(this::convertToTournamentDTO);
    }

    // Method to create a new tournament and return as DTO
    public TournamentDTO createTournament(Tournament tournament) {
        Tournament savedTournament = tournamentRepository.save(tournament);
        return convertToTournamentDTO(savedTournament);
    }

    // Delete a tournament by ID
    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }

    // Convert Tournament entity to TournamentDTO
    private TournamentDTO convertToTournamentDTO(Tournament tournament) {
        TournamentDTO tournamentDTO = new TournamentDTO();
        tournamentDTO.setId(tournament.getId());
        tournamentDTO.setName(tournament.getName());
        tournamentDTO.setLocation(tournament.getLocation());
        tournamentDTO.setStartDate(tournament.getStartDate());
        tournamentDTO.setEndDate(tournament.getEndDate());

        List<MatchDTO> matchDTOs = tournament.getMatches().stream()
                .map(matchService::convertToMatchDTO)
                .collect(Collectors.toList());
        tournamentDTO.setMatches(matchDTOs);

        return tournamentDTO;
    }
}
