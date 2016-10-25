package com.tennisKata.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "T_SET")
@EqualsAndHashCode(callSuper=true)
public @Data class Set extends MyEntity {

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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Game> games;

	private String score;

	@JoinColumn(name = "WINNER_ID")
	@ManyToOne(fetch = FetchType.EAGER)
	private Player winner;

	@JsonIgnore
	@JoinColumn(name = "MATCH_ID")
	@ManyToOne
	private Match match;

	public Set() {
		super();
		this.score = "0 - 0";
		this.winner = null;
	}

	public Set(Player firstPlayer, Player secondPlayer, List<Game> games, String score, Player winner, Match match) {
		super();
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.games = games;
		this.score = score;
		this.winner = winner;
		this.match = match;
	}

	public Set(Player firstPlayer, Player secondPlayer) {
		super();
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.firstPlayer.setGamesScore(0);
		this.secondPlayer.setGamesScore(0);
		final Game game = new Game(this.firstPlayer, this.secondPlayer);
		this.games = new ArrayList<Game>(Arrays.asList(game));
		this.score = "0 - 0";
		this.winner = null;
	}

	public void addGame(Game game) {
		games.add(game);
	}

}
