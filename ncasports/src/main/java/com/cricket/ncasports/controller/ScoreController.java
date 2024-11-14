package com.cricket.ncasports.controller;

import com.cricket.ncasports.dto.ScoreDTO;
import com.cricket.ncasports.model.Match;
import com.cricket.ncasports.model.Score;
import com.cricket.ncasports.repository.MatchRepository;
import com.cricket.ncasports.repository.ScoreRepository;
import com.cricket.ncasports.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    private final ScoreService scoreService;
    private final SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    public ScoreController(ScoreService scoreService, SimpMessagingTemplate messagingTemplate) {
        this.scoreService = scoreService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/create")
    public ResponseEntity<ScoreDTO> createScore(@RequestParam Long matchId) {
        try {
            Match match = matchRepository.findById(matchId)
                    .orElseThrow(() -> new IllegalArgumentException("Match not found"));

            Score score = new Score();
            score.setMatch(match);
            scoreRepository.save(score);

            return ResponseEntity.status(HttpStatus.CREATED).body(convertToScoreDTO(score));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


 // Update score for a match
    @PutMapping("/{matchId}")
    public ResponseEntity<ScoreDTO> updateScore(@PathVariable Long matchId, @RequestBody ScoreDTO scoreDTO) {
        Score score = scoreRepository.findByMatchId(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Score not found"));

        // Update score fields
        score.setTeamAScore(scoreDTO.getTeamAScore());
        score.setTeamBScore(scoreDTO.getTeamBScore());
        score.setWinner(scoreDTO.getWinner());
        score.setTeamAWickets(scoreDTO.getTeamAWickets());
        score.setTeamBWickets(scoreDTO.getTeamBWickets());
        score.setTeamAOver(scoreDTO.getTeamAOver());
        score.setTeamBOver(scoreDTO.getTeamBOver());

        score = scoreRepository.save(score);

        return ResponseEntity.ok(convertToScoreDTO(score));
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<ScoreDTO> getScore(@PathVariable Long matchId) {
        // Check if score exists
        Score score = scoreRepository.findByMatchId(matchId).orElseGet(() -> {
            // If no score, create a default score entry
            Match match = matchRepository.findById(matchId)
                    .orElseThrow(() -> new IllegalArgumentException("Match not found"));

            Score newScore = new Score();
            newScore.setMatch(match);
            newScore.setTeamAScore(0);  // Default score is 0
            newScore.setTeamBScore(0);  // Default score is 0
            newScore.setWinner("");     // No winner by default
            newScore.setTeamAWickets(0); // Default wickets
            newScore.setTeamBWickets(0); // Default wickets
            newScore.setTeamAOver(0.0f); // Default overs
            newScore.setTeamBOver(0.0f); // Default overs

            scoreRepository.save(newScore); // Save the default score
            return newScore;
        });

        // Return the score as DTO
        ScoreDTO scoreDTO = convertToScoreDTO(score);
        return ResponseEntity.ok(scoreDTO);
    }


    
    
    private ScoreDTO convertToScoreDTO(Score score) {
        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setMatchId(score.getMatch().getId());
        scoreDTO.setTeamA(score.getMatch().getTeamA());
        scoreDTO.setTeamB(score.getMatch().getTeamB());
        scoreDTO.setTeamAScore(score.getTeamAScore());
        scoreDTO.setTeamBScore(score.getTeamBScore());
        scoreDTO.setWinner(score.getWinner());
        scoreDTO.setTeamAWickets(score.getTeamAWickets());
        scoreDTO.setTeamBWickets(score.getTeamBWickets());
        scoreDTO.setTeamAOver(score.getTeamAOver());
        scoreDTO.setTeamBOver(score.getTeamBOver());
        return scoreDTO;
    }
}
