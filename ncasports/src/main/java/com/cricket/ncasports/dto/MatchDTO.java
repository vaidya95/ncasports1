package com.cricket.ncasports.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MatchDTO {

	private Long id;
    private String teamA;
    private String teamB;
    private LocalDateTime scheduledDate;
    private String tossWinner;
    private String tossDecision;
    private Long tournamentId;
    private String tournamentName;
    
    // List of players with their roles
    private List<PlayerDTO> teamAPlayers;
    private List<PlayerDTO> teamBPlayers;
	    
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
		public Long getTournamentId() {
			return tournamentId;
		}
		public void setTournamentId(Long tournamentId) {
			this.tournamentId = tournamentId;
		}
		public String getTournamentName() {
			return tournamentName;
		}
		public void setTournamentName(String tournamentName) {
			this.tournamentName = tournamentName;
		}
		
		
		public List<PlayerDTO> getTeamAPlayers() {
			return teamAPlayers;
		}
		public void setTeamAPlayers(List<PlayerDTO> teamAPlayers) {
			this.teamAPlayers = teamAPlayers;
		}
		public List<PlayerDTO> getTeamBPlayers() {
			return teamBPlayers;
		}
		public void setTeamBPlayers(List<PlayerDTO> teamBPlayers) {
			this.teamBPlayers = teamBPlayers;
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
	    
	    
	    
}
