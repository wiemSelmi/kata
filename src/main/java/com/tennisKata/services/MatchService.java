package com.tennisKata.services;

import org.springframework.transaction.annotation.Transactional;

import com.tennisKata.exceptions.MyBusinessException;
import com.tennisKata.models.Match;

@Transactional
public interface MatchService {

	/**
	 * calculate and update the score of a given match
	 * To win a match : Player must win 2 sets
	 * @param match
	 * @throws MyBusinessException
	 */
	void calculateScore(Match match) throws MyBusinessException;

	/**
	 * find a match by given id
	 * @param id
	 * @return
	 */
	Match findById(Integer id);

	/**
	 * save given match
	 * @param match
	 * @return
	 */
	Match save(Match match);

}
