package com.cricket.ncasports.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cricket.ncasports.dto.TournamentDTO;
import com.cricket.ncasports.model.Tournament;
import com.cricket.ncasports.repository.TournamentRepository;
import com.cricket.ncasports.service.TournamentService;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;
    private final TournamentRepository tournamentRepository;

    @Autowired
    public TournamentController(TournamentService tournamentService, TournamentRepository tournamentRepository) {
        this.tournamentService = tournamentService;
        this.tournamentRepository = tournamentRepository;
    }

    @GetMapping
    public ResponseEntity<List<TournamentDTO>> getAllTournaments() {
        List<TournamentDTO> tournaments = tournamentService.getAllTournamentsAsDTO();
        if (tournaments == null) {
            tournaments = new ArrayList<>();
        }
        return ResponseEntity.ok(tournaments);
    }


    // Get tournament by ID with error handling
    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournamentById(@PathVariable Long id) {
        Optional<TournamentDTO> tournamentDTO = tournamentService.getTournamentById(id);
        return tournamentDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Return 404 if not found
    }

    
    // Create a new tournament
    @PostMapping
    public ResponseEntity<TournamentDTO> createTournament(@RequestBody Tournament tournament) {
        TournamentDTO createdTournamentDTO = tournamentService.createTournament(tournament);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTournamentDTO); // Return 201 with created DTO
    }

    // Delete a tournament by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        Optional<TournamentDTO> tournamentDTO = tournamentService.getTournamentById(id);
        if (tournamentDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if tournament does not exist
        }
        tournamentService.deleteTournament(id);
        return ResponseEntity.noContent().build(); // Return 204 if deletion was successful
    }
}
