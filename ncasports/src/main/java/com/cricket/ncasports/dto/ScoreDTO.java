package com.cricket.ncasports.dto;

import jakarta.persistence.Column;

public class ScoreDTO {

    private Long matchId;
    private String teamA;
    private String teamB;
    private int teamAScore;
    private int teamBScore;
    private String winner;
    private int teamAWickets;
    private int teamBWickets;
    
    private float teamAOver;
    
    private float teamBOver=0.0f;;  // Use float for overs
    
    

    public ScoreDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScoreDTO(Long matchId, String teamA, String teamB, int teamAScore, int teamBScore, String winner,
                    int teamAWickets, int teamBWickets, float teamAOver, float teamBOver) {
        this.matchId = matchId;
        this.teamA = teamA;
        this.teamB = teamB;
        this.teamAScore = teamAScore;
        this.teamBScore = teamBScore;
        this.winner = winner;
        this.teamAWickets = teamAWickets;
        this.teamBWickets = teamBWickets;
        this.teamAOver = teamAOver;
        this.teamBOver = teamBOver;
    }

    // Getters and setters
    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
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
