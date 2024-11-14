package com.cricket.ncasports.dto;

import com.cricket.ncasports.model.Role;

public class PlayerDTO {
	    private Long id; 
	    private String playerName;
	    private Role role;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getPlayerName() {
			return playerName;
		}
		public void setPlayerName(String playerName) {
			this.playerName = playerName;
		}
		public Role getRole() {
			return role;
		}
		public void setRole(Role role) {
			this.role = role;
		}
		public PlayerDTO(Long id, String playerName, Role role) {
			super();
			this.id = id;
			this.playerName = playerName;
			this.role = role;
		}
		public PlayerDTO() {
			super();
			// TODO Auto-generated constructor stub
		}
		public PlayerDTO(String playerName, Role role) {
			super();
			this.playerName = playerName;
			this.role = role;
		}
	    
	    
	    
}
