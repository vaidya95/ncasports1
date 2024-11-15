package com.cricket.ncasports.model;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "cricket_match")
@JsonIgnoreProperties("tournament")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamA;
    private String teamB;
    private LocalDateTime scheduledDate;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    @JsonBackReference
    private Tournament tournament;

    @OneToOne(mappedBy = "match", cascade = CascadeType.ALL)
    private Score score;
    private String tossWinner;
    private String tossDecision;
    
    
    // New fields to store the playing lineup and their roles
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private List<Player> teamAPlayers; // Players for team A

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private List<Player> teamBPlayers; // Players for team B
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTeamA() {
		return teamA;
	}
	public void setTeamA(String teamA) {
		this.teamA = teamA;
	}
	public String getTeamB() {
		return teamB;
	}
	public void setTeamB(String teamB) {
		this.teamB = teamB;
	}
	public LocalDateTime getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(LocalDateTime scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	public Tournament getTournament() {
		return tournament;
	}
	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}
	public Score getScore() {
		return score;
	}
	public void setScore(Score score) {
		this.score = score;
	}
	
	public String getTossWinner() {
		return tossWinner;
	}
	public void setTossWinner(String tossWinner) {
		this.tossWinner = tossWinner;
	}
	public String getTossDecision() {
		return tossDecision;
	}
	public void setTossDecision(String tossDecision) {
		this.tossDecision = tossDecision;
	}
	public List<Player> getTeamAPlayers() {
		return teamAPlayers;
	}
	public void setTeamAPlayers(List<Player> teamAPlayers) {
		this.teamAPlayers = teamAPlayers;
	}
	public List<Player> getTeamBPlayers() {
		return teamBPlayers;
	}
	public void setTeamBPlayers(List<Player> teamBPlayers) {
		this.teamBPlayers = teamBPlayers;
	}

   
    
    
}
