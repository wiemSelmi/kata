package com.tennisKata.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.tennisKata.exceptions.MyBusinessException;
import com.tennisKata.models.Game;

/**
 * @author wselmi
 *
 */
@Transactional
public interface GameService {

	/**
	 * calculate and update score of a given game
	 * 
	 * @param game
	 * @throws MyBusinessException
	 */
	void calculateScore(Game game) throws MyBusinessException;

	/**
	 * save a given game
	 * 
	 * @param game
	 * @return
	 */
	Game save(Game game);

	/**
	 * find a game by given id
	 * 
	 * @param Id
	 * @return
	 */
	Game findById(Integer id);

	List<Game> findAll();
 
}
