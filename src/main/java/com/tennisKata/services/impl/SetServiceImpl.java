package com.tennisKata.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tennisKata.dao.SetDAO;
import com.tennisKata.exceptions.MyBusinessException;
import com.tennisKata.models.Game;
import com.tennisKata.models.Player;
import com.tennisKata.models.ScoreEnum;
import com.tennisKata.models.Set;
import com.tennisKata.models.GameTypeEnum;
import com.tennisKata.services.GameService;
import com.tennisKata.services.PlayerService;
import com.tennisKata.services.SetService;
import com.tennisKata.utils.TennisConstants;

@Service
public class SetServiceImpl implements SetService {

	@Autowired
	private SetDAO setDao;

	@Autowired
	private GameService gameService;

	@Autowired
	private PlayerService playerService;

	@Override
	public Set save(Set set) {
		return setDao.save(set);
	}

	@Override
	public Set findById(Integer id) {
		return setDao.findById(id);
	}

	@Override
	public void calculateScore(Set set) throws MyBusinessException {

		Player firstPlayer = set.getFirstPlayer();
		Player secondPlayer = set.getSecondPlayer();
		Player winner = null;
		String score;
		final int currentGameIndex = set.getGames().size() - 1;
		final Game currentGame = set.getGames().get(currentGameIndex);
		gameService.calculateScore(currentGame);

		boolean haveWonAtLeast6Games = firstPlayer.getGamesScore() >= TennisConstants.NUMBER_OF_GAMES_TO_WIN_SET
				|| secondPlayer.getGamesScore() >= TennisConstants.NUMBER_OF_GAMES_TO_WIN_SET;
		boolean haveWon2GamesMoreThanHisOppnent = Math
				.abs(firstPlayer.getGamesScore() - secondPlayer.getGamesScore()) >= 2;
		boolean haveWon1GameMoreThanHisOppnen = Math
				.abs(firstPlayer.getGamesScore() - secondPlayer.getGamesScore()) == 1;
		boolean lastGameWasTieBreak = GameTypeEnum.TIEBREAK.equals(currentGame.getType());

		// case 1 : set is won
		if ((haveWonAtLeast6Games && haveWon2GamesMoreThanHisOppnent)
				|| (lastGameWasTieBreak && haveWon1GameMoreThanHisOppnen)) {
			// update sets score of the winner
			winner = firstPlayer.getGamesScore() > secondPlayer.getGamesScore() ? firstPlayer : secondPlayer;
			playerService.winASet(winner);
			set.setWinner(winner);
			score = winner.getName() + " " + ScoreEnum.WON;

		}

		// case 2 : set is not closed yet
		else {
			score = firstPlayer.getGamesScore() + " - " + secondPlayer.getGamesScore();
		}

		// update set
		set.setScore(score);
		if (winner == null) {
			if (currentGame.getWinner() != null) {
				if (firstPlayer.getGamesScore() == 6 && secondPlayer.getGamesScore() == 6) {
					set.addGame(new Game(firstPlayer, secondPlayer, GameTypeEnum.TIEBREAK));
				} else {
					set.addGame(new Game(firstPlayer, secondPlayer));
				}
			}
		}
		setDao.save(set);
	}

}
