package com.cricket.ncasports.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Player {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Enumerated(EnumType.STRING)
    private Role role; // Direct role field
  
    
    
    
	public Player(String name,Role role) {
		super();
		this.name = name;
		this.role = role;
	}
	
	

	public Player(Long id, String name, Role role) {
		super();
		this.id = id;
		this.name = name;
		this.role = role;
	}



	public Player() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Role getRole() {
		return role;
	}



	public void setRole(Role role) {
		this.role = role;
	}



}