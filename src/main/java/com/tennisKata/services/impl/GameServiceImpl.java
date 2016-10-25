package com.tennisKata.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tennisKata.dao.GameDAO;
import com.tennisKata.dao.PlayerDAO;
import com.tennisKata.exceptions.MyBusinessException;
import com.tennisKata.models.Game;
import com.tennisKata.models.Player;
import com.tennisKata.models.ScoreEnum;
import com.tennisKata.models.GameTypeEnum;
import com.tennisKata.services.GameService;
import com.tennisKata.utils.TennisConstants;

@Service
public class GameServiceImpl implements GameService {

	private static final Logger LOG = LoggerFactory.getLogger(GameServiceImpl.class);

	@Autowired
	private GameDAO gameDao;

	@Autowired
	private PlayerDAO playerDao;
	
	@Override
	public Game save(Game game){
		return gameDao.save(game);
	}
	
	@Override
	public Game findById(Integer id){
		return gameDao.findById(id);
	}

	@Override
	public void calculateScore(Game game) throws MyBusinessException {
		if (GameTypeEnum.TIEBREAK.equals(game.getType())) {
			calculateTieBreakGameScore(game);
		} else if (GameTypeEnum.NOTTIEBREAK.equals(game.getType())) {
			calculateNotTieBreakGameScore(game);
		} else {
			LOG.error("Unknown type of game");
			throw new MyBusinessException("Unknown type of game");
		}
	}

	/**
	 * Calculate and update score of a not tie break game
	 * To win a game : Player must have at least 4 points and 2 points more than his opponent
	 * @param game
	 */
	private void calculateNotTieBreakGameScore(Game game) {
		final Player firstPlayer = game.getFirstPlayer();
		final Player secondPlayer = game.getSecondPlayer();
		String score;
		Player winner = null;

		// step 1 : calculate score
		if (firstPlayer.getPointsScore() >= TennisConstants.NUMBER_OF_POINTS_TO_WIN_GAME || secondPlayer.getPointsScore() >= TennisConstants.NUMBER_OF_POINTS_TO_WIN_GAME) {
			if (Math.abs(secondPlayer.getPointsScore() - firstPlayer.getPointsScore()) >= 2) {
				Player winnerPlayer = getWinnerPlayer(firstPlayer, secondPlayer);
				score = winnerPlayer.getName() + " " + ScoreEnum.WON;
				winner = winnerPlayer;

			} else if (firstPlayer.getPointsScore() == secondPlayer.getPointsScore()) {
				score = ScoreEnum.DEUCE.toString();
			} else {
				score = ScoreEnum.ADVANTAGE + " " + getWinnerPlayer(firstPlayer, secondPlayer).getName();
			}
		} else if (firstPlayer.getPointsScore() == TennisConstants.NUMBER_OF_POINTS_TO_HAVE_DEUCE && secondPlayer.getPointsScore() == TennisConstants.NUMBER_OF_POINTS_TO_HAVE_DEUCE) {
			score = ScoreEnum.DEUCE.toString();
		} else {
			score = ScoreEnum.getScore(firstPlayer.getPointsScore()) + " - "
					+ ScoreEnum.getScore(secondPlayer.getPointsScore());
		}

		// step 2 : update game
		game.setScore(score);
		if (winner != null) {
			// update games score of the winner
			winner.setGamesScore(winner.getGamesScore() + 1);
			playerDao.save(winner);

			game.setWinner(winner);
		}
		gameDao.save(game);

	}

	/**
	 * calculate and update score of a tie break game
	 * To win a tie break game : Player must have at least 7 points and 2 points more than his opponent
	 * 
	 * @param game
	 */
	private void calculateTieBreakGameScore(Game game) {
		Player firstPlayer = game.getFirstPlayer();
		Player secondPlayer = game.getSecondPlayer();
		String score;
		Player winner = null;

		// step1 : calculate score
		final boolean haveAtLeast7Points = firstPlayer.getPointsScore() >= TennisConstants.NUMBER_OF_POINTS_TO_WIN_TIEBREAKGAME || secondPlayer.getPointsScore() >= TennisConstants.NUMBER_OF_POINTS_TO_WIN_TIEBREAKGAME;
		final boolean have2PointsMoreThanTheOppenent = Math
				.abs(secondPlayer.getPointsScore() - firstPlayer.getPointsScore()) >= 2;
		if (haveAtLeast7Points && have2PointsMoreThanTheOppenent) {
			Player winnerPlayer = getWinnerPlayer(firstPlayer, secondPlayer);
			score = winnerPlayer.getName() + " " + ScoreEnum.WON;
			winner = winnerPlayer;

		} else {
			score = firstPlayer.getPointsScore() + " - " + secondPlayer.getPointsScore();
		}

		// step 2 : update game
		game.setScore(score);
		if (winner != null) {
			// update games score of the winner
			winner.setGamesScore(winner.getGamesScore() + 1);
			playerDao.save(winner);

			game.setWinner(winner);
		}
		gameDao.save(game);
	}

	/**
	 * retrieve the player with the highest points score
	 * @param player1
	 * @param player2
	 * @return
	 */
	private Player getWinnerPlayer(Player player1, Player player2) {

		return player1.getPointsScore() > player2.getPointsScore() ? player1 : player2;
	}

	@Override
	public List<Game> findAll() {
 		return gameDao.findAll();
	}

}
