package com.tennisKata.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tennisKata.config.AppConfigTest;
import com.tennisKata.models.Game;
import com.tennisKata.models.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfigTest.class)
public class GameServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(GameServiceTest.class);

	@Autowired
	private GameService gameService;

	@Autowired
	private PlayerService playerService;

	// tests

	@Test
	public void gameShouldStartByLoveForScore() {
		LOG.debug("-> gameShouldStartByLoveForScore");
		// Given
		final Game game = gameService.save(buildGame());

		// Then
		final String loveScore = "LOVE - LOVE";
		assertEquals(loveScore, game.getScore());
		LOG.debug("<- gameShouldStartByLoveForScore : " + game.getScore());
	}

	@Test
	public void gameShouldStartWithNoWinner() {
		LOG.debug("-> gameShouldStartWithNoWinner");
		// Given
		final Game game = gameService.save(buildGame());

		// Then
		assertNull(game.getWinner());
		LOG.debug("<- gameShouldStartWithNoWinner");
	}

	@Test
	public void scoreShouldBeFifteenLoveWhenAFirstPlayerWinAPoint() throws Exception {
		LOG.debug("-> scoreShouldBeFifteenLoveWhenAFirstPlayerWinAPoint");
		// Given
		Game game = gameService.save(buildGame());

		// When
		final Player player1 = game.getFirstPlayer();
		playerService.winAPoint(player1);

		gameService.calculateScore(game);

		// Then
		game = gameService.findById(game.getId());
		assertEquals("FIFTEEN - LOVE", game.getScore());
		assertNull(game.getWinner());
		LOG.debug("<- scoreShouldBeFifteenLoveWhenAFirstPlayerWinAPoint , score = " + game.getScore());
	}

	@Test
	public void scoreShouldBeLoveFortyWhenSecondPlayerWin3Points() throws Exception {
		LOG.debug("-> scoreShouldBeLoveFortyWhenSecondPlayerWin3Points");
		// Given
		Game game = gameService.save(buildGame());

		// When
		final Player player2 = game.getSecondPlayer();
		winPointsBy(player2, 3);
		gameService.calculateScore(game);

		// Then
		game = gameService.findById(game.getId());
		assertEquals("LOVE - FORTY", game.getScore());
		assertNull(game.getWinner());
		LOG.debug("<- scoreShouldBeLoveFortyWhenSecondPlayerWin3Points , score = " + game.getScore());
	}

	@Test
	public void scoreShouldBeDeuceWhenItIsFortyForty() throws Exception {
		LOG.debug("-> scoreShouldBeDeuceWhenIsFortyForty");
		// Given
		Game game = gameService.save(buildGame());

		// When
		final Player player1 = game.getFirstPlayer();
		winPointsBy(player1, 3);

		final Player player2 = game.getSecondPlayer();
		winPointsBy(player2, 3);

		gameService.calculateScore(game);

		// Then
		game = gameService.findById(game.getId());
		assertEquals("DEUCE", game.getScore());
		assertNull(game.getWinner());
		LOG.debug("<- scoreShouldBeDeuceWhenIsFortyForty , score = " + game.getScore());
	}

	@Test
	public void scoreShouldBeDeuceWhenThePlayerNotInAdvantageWin1Point() throws Exception {
		LOG.debug("-> scoreShouldBeDeuceWhenThePlayerNotInAdvantageWin1Point");
		// Given
		Game game = gameService.save(buildGame());

		// When
		// at least 3 points
		final Player player1 = game.getFirstPlayer();
		winPointsBy(player1, 3);

		// at least 3 points
		final Player player2 = game.getSecondPlayer();
		winPointsBy(player2, 3);

		// the point to be advantage
		playerService.winAPoint(player2);

		// the point to be advantage
		playerService.winAPoint(player1);

		gameService.calculateScore(game);

		// Then
		game = gameService.findById(game.getId());
		assertEquals("DEUCE", game.getScore());
		assertNull(game.getWinner());
		LOG.debug("<- scoreShouldBeDeuceWhenThePlayerNotInAdvantageWin1Point , score = " + game.getScore());
	}

	@Test
	public void scoreShouldBeAvantageForThePlayerThatHasAtLeast3PointsAnd1PointMoreThanHisOpponent() throws Exception {
		LOG.debug("-> scoreShouldBeAvantageForThePlayerThatHasAtLeast3PointsAnd1PointMoreThanHisOpponent");
		// Given
		Game game = gameService.save(buildGame());

		// When
		// at least 3 points
		final Player player1 = game.getFirstPlayer();
		winPointsBy(player1, 3);

		// at least 3 points
		final Player player2 = game.getSecondPlayer();
		winPointsBy(player2, 3);

		// the point to be advantage
		playerService.winAPoint(player2);

		gameService.calculateScore(game);

		// Then
		game = gameService.findById(game.getId());
		final String expectedScore = "ADVANTAGE " + player2.getName();
		assertNull(game.getWinner());
		assertEquals(expectedScore, game.getScore());
		LOG.debug("<- scoreShouldBeAvantageForThePlayerThatHasAtLeast3PointsAnd1PointMoreThanHisOpponent , score = "
				+ game.getScore());
	}

	@Test
	public void gameShouldBeWonByThePlayerInAdvantageThatWinAnotherPoint() throws Exception {
		LOG.debug("-> gameShouldBeWonByThePlayerInAdvantageThatWinAnotherPoint");
		// Given
		Game game = gameService.save(buildGame());

		// When
		// at least 3 points
		final Player player1 = game.getFirstPlayer();
		winPointsBy(player1, 3);

		// at least 3 points
		final Player player2 = game.getSecondPlayer();
		winPointsBy(player2, 3);

		// the point to be advantage
		playerService.winAPoint(player2);

		// the point to win
		playerService.winAPoint(player2);

		gameService.calculateScore(game);

		// Then
		game = gameService.findById(game.getId());
		final String expectedScore = player2.getName() + " WON";
		assertEquals(expectedScore, game.getScore());
		assertNotNull(game.getWinner());
		assertEquals(player2, game.getWinner());
		LOG.debug("<- gameShouldBeWonByThePlayerInAdvantageThatWinAnotherPoint , score = " + game.getScore());
	}

	@Test
	public void gameShouldBeWonByThePlayerThatWonAtLeast4PointsAndHas2PointsMoreThanHisOpponent() throws Exception {
		LOG.debug("-> gameShouldBeWonByThePlayerThatWonAtLeast4PointsAndHas2PointsMoreThanHisOpponent");
		// Given
		Game game = gameService.save(buildGame());

		// When
		// 2 points
		Player player1 = game.getFirstPlayer();
		winPointsBy(player1, 2);

		// at least 4 points
		Player player2 = game.getSecondPlayer();
		winPointsBy(player2, 4);

		gameService.calculateScore(game);

		// Then
		game = gameService.findById(game.getId());
		player1 = playerService.findById(player1.getId());
		player2 = playerService.findById(player2.getId());

		assertEquals(Integer.valueOf(4), player2.getPointsScore());
		assertEquals(Integer.valueOf(2), player1.getPointsScore());
		final String expectedScore = player2.getName() + " WON";
		assertEquals(expectedScore, game.getScore());
		assertNotNull(game.getWinner());
		assertEquals(player2, game.getWinner());
		LOG.debug("<- gameShouldBeWonByThePlayerThatWonAtLeast4PointsAndHas2PointsMoreThanHisOpponent , score = "
				+ game.getScore());
	}

	// Utils
	private final Player createPlayer() {
		LOG.debug("-> createPlayer");
		String name = RandomStringUtils.randomAlphanumeric(20);
		final Player player = playerService.save(new Player(name));
		LOG.debug("<- createPlayer : " + player.getName());
		return player;

	}

	private Game buildGame() {
		LOG.debug("-> createGame");
		Player player1 = createPlayer();
		Player player2 = createPlayer();
		Game game = new Game(player1, player2);
		LOG.debug("<- createGame : " + game);
		return game;
	}

	private void winPointsBy(Player player, Integer nbrToWin) {
		for (int i = 0; i < nbrToWin; i++) {
			playerService.winAPoint(player);
		}
	}

}
