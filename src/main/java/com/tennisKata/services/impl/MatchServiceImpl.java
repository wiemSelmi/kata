package com.tennisKata.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tennisKata.dao.MatchDAO;
import com.tennisKata.exceptions.MyBusinessException;
import com.tennisKata.models.Game;
import com.tennisKata.models.Match;
import com.tennisKata.models.Player;
import com.tennisKata.models.Set;
import com.tennisKata.services.MatchService;
import com.tennisKata.services.SetService;
import com.tennisKata.utils.TennisConstants;

@Service
public class MatchServiceImpl implements MatchService {

	@Autowired
	private MatchDAO matchDao;
	@Autowired
	private SetService setService;
	
	@Override
	public Match save (Match match){
		return matchDao.save(match);
	}
	
	@Override
	public Match findById (Integer id){
		return matchDao.findById(id);
	}

	@Override
	public void calculateScore(Match match) throws MyBusinessException {

		Player firstPlayer = match.getFirstPlayer();
		Player secondPlayer = match.getSecondPlayer();
		Player winner = null;
		String score;
		final int currentSetIndex = match.getSets().size() - 1;
		final Set currentSet = match.getSets().get(currentSetIndex);
		setService.calculateScore(currentSet);

		// Check if the match is won : To win a match the player must win at
		// least 2 sets
		if (firstPlayer.getSetsScore() == TennisConstants.NUMBER_OF_SETS_TO_WIN_MATCH
				|| secondPlayer.getSetsScore() == TennisConstants.NUMBER_OF_SETS_TO_WIN_MATCH) {

			winner = firstPlayer.getSetsScore() > secondPlayer.getSetsScore() ? firstPlayer : secondPlayer;
			match.setWinner(winner);
			score = "Game, set, match " + winner.getName();
		} else {
			final String SetsScore = firstPlayer.getSetsScore() + " - " + secondPlayer.getSetsScore();
			final Game currenGame = currentSet.getGames().get(currentSet.getGames().size() - 1);
			score = "Game [" + currenGame.getScore() + "], Set [" + currentSet.getScore() + "], Match [" + SetsScore
					+ "]";
			if (currentSet.getWinner() != null) {
				match.addSet(new Set(firstPlayer, secondPlayer));
			}
		}
		
		match.setScore(score);
		matchDao.save(match);

	}
}
