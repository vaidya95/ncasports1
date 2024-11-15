package com.cricket.ncasports.controller;

import com.cricket.ncasports.dto.MatchDTO;
import com.cricket.ncasports.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    // Get all matches
    @GetMapping
    public ResponseEntity<List<MatchDTO>> getAllMatches() {
        List<MatchDTO> matches = matchService.getAllMatchesAsDTO();
        if (matches.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Return 204 if no matches
        }
        return ResponseEntity.ok(matches); // Return 200 with list of matches
    }

    // Get match by ID
    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatchById(@PathVariable Long id) {
        Optional<MatchDTO> matchDTO = matchService.getMatchById(id);
        return matchDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Return 404 if not found
    }

    // Create a new match
    @PostMapping
    public ResponseEntity<MatchDTO> createMatch(@RequestBody MatchDTO matchDTO) {
        try {
            MatchDTO createdMatchDTO = matchService.createMatch(matchDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMatchDTO); // Return 201 with created DTO
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Return 400 if there's an error
        }
    }

    // Update the match
    @PutMapping("/{id}")
    public ResponseEntity<MatchDTO> updateMatch(@PathVariable Long id, @RequestBody MatchDTO matchDTO) {
        Optional<MatchDTO> match = matchService.getMatchById(id);
        if (match.isPresent()) {
            MatchDTO updatedMatchDTO = matchService.updateMatch(id, matchDTO);
            return ResponseEntity.ok(updatedMatchDTO); // Return 200 with updated DTO
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if match not found
    }

    // Delete a match by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        Optional<MatchDTO> matchDTO = matchService.getMatchById(id);
        if (matchDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if match does not exist
        }
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build(); // Return 204 if deletion was successful
    }
}
