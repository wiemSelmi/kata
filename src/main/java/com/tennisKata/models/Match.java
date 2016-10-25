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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "T_MATCH")
@EqualsAndHashCode(callSuper=true)
public @Data class Match extends MyEntity {

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
	private List<Set> sets;

	@JoinColumn(name = "WINNER_ID")
	@ManyToOne(fetch = FetchType.EAGER)
	private Player winner;

	private String score;

	public Match(Player firstPlayer, Player secondPlayer, List<Set> sets, Player winner, String score) {
		super();
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.sets = sets;
		this.winner = winner;
		this.score = score;
	}

	public Match(Player firstPlayer, Player secondPlayer) {
		super();
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.firstPlayer.setSetsScore(0);
		this.secondPlayer.setSetsScore(0);
		final Set set = new Set(this.firstPlayer, this.secondPlayer);
		this.sets = new ArrayList<Set>(Arrays.asList(set));
		this.score = "Game [LOVE - LOVE], Set [0 - 0], Match [0 - 0]";

	}

	public Match() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void addSet(Set set) {
		sets.add(set);
	}

}
