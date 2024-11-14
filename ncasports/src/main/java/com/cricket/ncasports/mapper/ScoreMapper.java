package com.cricket.ncasports.mapper;

import com.cricket.ncasports.dto.ScoreDTO;
import com.cricket.ncasports.model.Score;
import org.springframework.stereotype.Component;

@Component
public class ScoreMapper {

    // Convert Score entity to ScoreDTO
    public ScoreDTO toDTO(Score score) {
        if (score == null) {
            return null;
        }
        
        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setMatchId(score.getId());
        scoreDTO.setTeamAScore(score.getTeamAScore());
        scoreDTO.setTeamBScore(score.getTeamBScore());
        scoreDTO.setWinner(score.getWinner());
        
        return scoreDTO;
    }
    
    // Convert ScoreDTO to Score entity
    public Score toEntity(ScoreDTO scoreDTO) {
        if (scoreDTO == null) {
            return null;
        }

        Score score = new Score();
        score.setId(scoreDTO.getMatchId());
        score.setTeamAScore(scoreDTO.getTeamAScore());
        score.setTeamBScore(scoreDTO.getTeamBScore());
        score.setWinner(scoreDTO.getWinner());

        return score;
    }
}
