package com.cricket.ncasports.service;

import com.cricket.ncasports.dto.ScoreDTO;
import com.cricket.ncasports.model.Match;
import com.cricket.ncasports.model.Score;
import com.cricket.ncasports.repository.MatchRepository;
import com.cricket.ncasports.repository.ScoreRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ScoreUpdateService scoreUpdateService;

    // Fetch score details for a match
    public ScoreDTO getScoreByMatchId(Long matchId) {
        Optional<Score> optionalScore = scoreRepository.findByMatchId(matchId);

        // Check if score exists, if so return the DTO with required data, else return null
        if (optionalScore.isPresent()) {
            Score score = optionalScore.get();
            return new ScoreDTO(
                    score.getMatch().getId(),
                    score.getMatch().getTeamA(),
                    score.getMatch().getTeamB(),
                    score.getTeamAScore(),
                    score.getTeamBScore(),
                    score.getWinner(),
                    score.getTeamAWickets(),
                    score.getTeamBWickets(),
                    score.getTeamAOver(),
                    score.getTeamBOver()
            );
        } else {
            return null;  // Score not found
        }
    }

    // Create new score for a match
    public ScoreDTO createScore(ScoreDTO scoreDTO) {
        Match match = matchRepository.findById(scoreDTO.getMatchId())
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));

        Score score = new Score();
        score.setMatch(match);
        score.setTeamAScore(scoreDTO.getTeamAScore());
        score.setTeamBScore(scoreDTO.getTeamBScore());
        score.setWinner(scoreDTO.getWinner());
        score.setTeamAWickets(scoreDTO.getTeamAWickets());
        score.setTeamBWickets(scoreDTO.getTeamBWickets());
        score.setTeamAOver(scoreDTO.getTeamAOver());
        score.setTeamBOver(scoreDTO.getTeamBOver());

        score = scoreRepository.save(score);

        ScoreDTO createdScoreDTO = convertToScoreDTO(score);
        scoreUpdateService.sendScoreUpdate(createdScoreDTO);  // Send score update to WebSocket

        return createdScoreDTO;
    }

    // Update score for a specific match
    public ScoreDTO updateScore(ScoreDTO scoreDTO) {
        Match match = matchRepository.findById(scoreDTO.getMatchId())
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));

        Score score = scoreRepository.findByMatch(match)
                .orElseThrow(() -> new IllegalArgumentException("Score not found"));

        score.setTeamAScore(scoreDTO.getTeamAScore());
        score.setTeamBScore(scoreDTO.getTeamBScore());
        score.setWinner(scoreDTO.getWinner());
        score.setTeamAWickets(scoreDTO.getTeamAWickets());
        score.setTeamBWickets(scoreDTO.getTeamBWickets());
        score.setTeamAOver(scoreDTO.getTeamAOver());
        score.setTeamBOver(scoreDTO.getTeamBOver());

        score = scoreRepository.save(score);

        ScoreDTO updatedScoreDTO = convertToScoreDTO(score);
        scoreUpdateService.sendScoreUpdate(updatedScoreDTO);  // Send updated score via WebSocket

        return updatedScoreDTO;
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
        scoreDTO.setTeamAOver(score.getTeamAOver());  // Set overs as float
        scoreDTO.setTeamBOver(score.getTeamBOver());  // Set overs as float
        return scoreDTO;
    }
}
