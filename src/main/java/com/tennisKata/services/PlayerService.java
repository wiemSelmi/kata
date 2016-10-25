package com.tennisKata.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.tennisKata.exceptions.MyBusinessException;
import com.tennisKata.models.Match;
import com.tennisKata.models.Player;


@Transactional
public interface PlayerService {

	/**
	 * increment points score of a given player
	 * 
	 * @param player
	 */
	void winAPoint(Player player);

	/**
	 * increment games score of a given player
	 * 
	 * @param player
	 */
	void winAGame(Player player);

	/**
	 * increment sets score of a given player
	 * 
	 * @param player
	 */
	void winASet(Player player);

	/**
	 * save a given player
	 * 
	 * @param player
	 * @return
	 */
	Player save(Player player);

	/**
	 * save a player by given id
	 * 
	 * @param id
	 * @return
	 */
	Player findById(Integer id);

	/**
	 * increment points score of a given player and calculate score of match
	 * @param player
	 * @param match
	 */
	void winAPoint(Player player, Match match) throws MyBusinessException;

	/**
	 * retrieve all players
	 * @return
	 */
	List<Player> findAll();

}
