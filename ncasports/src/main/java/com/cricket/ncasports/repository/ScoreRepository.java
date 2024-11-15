package com.cricket.ncasports.repository;

import com.cricket.ncasports.model.Match;
import com.cricket.ncasports.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findByMatch(Match match);
    Optional<Score> findByMatchId(Long matchId);
}
