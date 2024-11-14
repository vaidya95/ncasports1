package com.cricket.ncasports.model;

import jakarta.persistence.*;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int teamAScore=0;
    private int teamBScore=0;
    private String winner;
    
    private int teamAWickets=0;
    private int teamBWickets=0;
    
    
    private Float teamAOver=0.0f;  // Use Float (wrapper class) instead of float

    
    private Float teamBOver=0.0f;  // Use Float (wrapper class) instead of float

    @OneToOne
    @JoinColumn(name = "match_id")
    private Match match;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTeamAScore() {
		return teamAScore;
	}

	public void setTeamAScore(int teamAScore) {
		this.teamAScore = teamAScore;
	}

	public int getTeamBScore() {
		return teamBScore;
	}

	public void setTeamBScore(int teamBScore) {
		this.teamBScore = teamBScore;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public int getTeamAWickets() {
		return teamAWickets;
	}

	public void setTeamAWickets(int teamAWickets) {
		this.teamAWickets = teamAWickets;
	}

	public int getTeamBWickets() {
		return teamBWickets;
	}

	public void setTeamBWickets(int teamBWickets) {
		this.teamBWickets = teamBWickets;
	}

	public float getTeamAOver() {
		return teamAOver;
	}

	public void setTeamAOver(float teamAOver) {
		this.teamAOver = teamAOver;
	}

	public float getTeamBOver() {
		return teamBOver;
	}

	public void setTeamBOver(float teamBOver) {
		this.teamBOver = teamBOver;
	}

    
}
