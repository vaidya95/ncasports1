package com.cricket.ncasports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cricket.ncasports.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {
    // Custom queries or methods can be added here as needed
}
