package com.cricket.ncasports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cricket.ncasports.model.Tournament;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
	
	@EntityGraph(attributePaths = "matches")
    List<Tournament> findAll();
}
