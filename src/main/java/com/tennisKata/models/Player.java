package com.tennisKata.models;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "T_PLAYER")
@EqualsAndHashCode(callSuper=true)
public @Data class Player extends MyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	@Column(name = "POINTS_SCORE")
	private Integer pointsScore;
	
	@Column(name = "GAMES_SCORE")
	private Integer gamesScore;
	
	@Column(name = "SETS_SCORE")
	private Integer setsScore;

	public Player() {
		super();
		this.pointsScore = 0;
		this.gamesScore = 0;
		this.setsScore = 0;
	}

	public Player(String name) {
		super();
		this.name = name;
		this.pointsScore = 0;
		this.gamesScore = 0;
		this.setsScore = 0;
	}

}
