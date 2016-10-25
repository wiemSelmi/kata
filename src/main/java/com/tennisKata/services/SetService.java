package com.tennisKata.services;

import org.springframework.transaction.annotation.Transactional;

import com.tennisKata.exceptions.MyBusinessException;
import com.tennisKata.models.Set;

@Transactional
public interface SetService {

	/**
	 * save given set
	 * 
	 * @param set
	 * @return
	 */
	Set save(Set set);

	/**
	 * find set by given id
	 * 
	 * @param id
	 * @return
	 */
	Set findById(Integer id);

	/**
	 * calculate and update the score of a given set To win a set : Player must
	 * win at least 6 games and 2 games more than his opponent
	 * 
	 * @param set
	 * @throws MyBusinessException
	 */
	void calculateScore(Set set) throws MyBusinessException;

}
