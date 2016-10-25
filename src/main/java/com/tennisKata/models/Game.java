package com.tennisKata.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "T_GAME")
@EqualsAndHashCode(callSuper = true)
public @Data class Game extends MyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "FIRST_PLAYER_ID")
	@ManyToOne(fetch = FetchType.EAGER)
	private Player firstPlayer;

	@JoinColumn(name = "SECOND_PLAYER_ID")
	@ManyToOne(fetch = FetchType.EAGER)
	private Player secondPlayer;

	private String score;

	@JoinColumn(name = "WINNER_ID")
	@ManyToOne(fetch = FetchType.EAGER)
	private Player winner;

	@Enumerated(EnumType.STRING)
	private GameTypeEnum type;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "SET_ID")
	private Set set;

	public Game() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Game(Player firstPlayer, Player secondPlayer, GameTypeEnum type) {
		super();
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.firstPlayer.setPointsScore(0);
		this.secondPlayer.setPointsScore(0);
		this.score = ScoreEnum.LOVE + " - " + ScoreEnum.LOVE;
		this.winner = null;
		this.type = type;
	}

	public Game(Player firstPlayer, Player secondPlayer) {
		super();
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.firstPlayer.setPointsScore(0);
		this.secondPlayer.setPointsScore(0);
		this.score = ScoreEnum.LOVE + " - " + ScoreEnum.LOVE;
		this.winner = null;
		this.type = GameTypeEnum.NOTTIEBREAK;
	}

}
