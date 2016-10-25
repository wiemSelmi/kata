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
import com.tennisKata.exceptions.MyBusinessException;
import com.tennisKata.models.Player;
import com.tennisKata.models.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfigTest.class)
public class SetServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(SetServiceTest.class);

	@Autowired
	private SetService setService;

	@Autowired
	private PlayerService playerService;
	
	// Tests

	@Test
	public void setShouldStartBy0ForScore() {
		LOG.debug("-> setShouldStartBy0ForScore");
		// Given
		final Set set = createSet();

		// Then
		assertEquals("0 - 0", set.getScore());
		LOG.debug("<- setShouldStartBy0ForScore : " + set.getScore());
	}

	@Test
	public void setShouldStartWithNoWinner() {
		LOG.debug("-> setShouldStartWithNoWinner");
		// Given
		final Set set = createSet();

		// Then
		assertNull(set.getWinner());
		LOG.debug("<- setShouldStartWithNoWinner");
	}

	@Test
	public void scoreShouldBeOneTwoWhenPlayerWin1GameAndHisOpponentWin2Games() throws MyBusinessException {
		LOG.debug("-> scoreShouldBeOneTwoWhenPlayerWin1GameAndHisOpponentWin2Games");
		// Given
		Set set = createSet();

		// when
		final Player player1 = set.getFirstPlayer();
		winGamesBy(player1, 1, set);

		final Player player2 = set.getSecondPlayer();
		winGamesBy(player2, 2, set);

		// Then
		set = setService.findById(set.getId());
		assertEquals("1 - 2", set.getScore());
		assertNull(set.getWinner());
		LOG.debug("<- scoreShouldBeOneTwoWhenPlayerWin1GameAndHisOpponentWin2Games");
	}

	@Test
	public void scoreShouldBeOneTwoWhenPlayerWin1GameAndHisOpponentWin2GamesAnd2Points() throws MyBusinessException {
		LOG.debug("-> scoreShouldBeOneTwoWhenPlayerWin1GameAndHisOpponentWin2GamesAnd2Points");
		// Given
		Set set = createSet();

		// when
		final Player player1 = set.getFirstPlayer();
		winGamesBy(player1, 1, set);

		final Player player2 = set.getSecondPlayer();
		winGamesBy(player2, 2, set);

		winPointsBy(player2, 2, set);

		// Then
		set = setService.findById(set.getId());
		assertEquals("1 - 2", set.getScore());
		assertNull(set.getWinner());
		LOG.debug(
				"<- scoreShouldBeOneTwoWhenPlayerWin1GameAndHisOpponentWin2GamesAnd2Points, score = " + set.getScore());
	}

	@Test
	public void setShouldBeWonByThePlayerWhoWonAtLeast6GamesAnd2GamesMoreThanHisOppnent() throws MyBusinessException {
		LOG.debug("-> setShouldBeWonByThePlayerWhoWonAtLeast6GamesAnd2GamesMoreThanHisOppnent");
		// Given
		Set set = createSet();

		// when
		final Player player1 = set.getFirstPlayer();
		winGamesBy(player1, 4, set);

		final Player player2 = set.getSecondPlayer();
		winGamesBy(player2, 6, set);

		// Then
		set = setService.findById(set.getId());
		final String expectedScore = player2.getName() + " WON";
		assertEquals(expectedScore, set.getScore());
		assertNotNull(set.getWinner());
		assertEquals(player2, set.getWinner());
		LOG.debug("<- setShouldBeWonByThePlayerWhoWonAtLeast6GamesAnd2GamesMoreThanHisOppnent, score = "
				+ set.getScore());
	}

	@Test
	public void scoreShouldBe6ForBothPlayersWhenBothWon6GamesWithoutDifferenceOf2Gmes() throws MyBusinessException {
		LOG.debug("-> scoreShouldBe6ForBothPlayersWhenBothWon6GamesWithoutDifferenceOf2Gmes");
		// Given
		Set set = createSet();

		// when
		final Player player1 = set.getFirstPlayer();
		winGamesBy(player1, 5, set);

		final Player player2 = set.getSecondPlayer();
		winGamesBy(player2, 6, set);

		winGamesBy(player1, 1, set);

		// Then
		set = setService.findById(set.getId());
		assertEquals("6 - 6", set.getScore());
		assertNull(set.getWinner());
		LOG.debug(
				"<- scoreShouldBe6ForBothPlayersWhenBothWon6GamesWithoutDifferenceOf2Gmes, score = " + set.getScore());
	}

	@Test
	public void setSchouldBeWonByThePlayerWhoWinTheTieBreakGame() throws MyBusinessException {
		LOG.debug("-> setSchouldBeWonByThePlayerWhoWonTheTieBreakGame");
		// Given
		Set set = createSet();

		// when
		final Player player1 = set.getFirstPlayer();
		winGamesBy(player1, 5, set);

		final Player player2 = set.getSecondPlayer();
		winGamesBy(player2, 6, set);

		winGamesBy(player1, 1, set);

		winTieBreakGame(player2, set);

		// Then
		set = setService.findById(set.getId());
		final String expectedScore = player2.getName() + " WON";
		assertEquals(expectedScore, set.getScore());
		assertNotNull(set.getWinner());
		assertEquals(player2, set.getWinner());
		LOG.debug("<- setSchouldBeWonByThePlayerWhoWonTheTieBreakGame, score = " + set.getScore());
	}

	// Utils
	private Player createPlayer() {
		LOG.debug("-> createPlayer");
		String name = RandomStringUtils.randomAlphanumeric(20);
		Player player = playerService.save(new Player(name));
		LOG.debug("<- createPlayer : " + player.getName());
		return player;

	}

	private Set createSet() {
		LOG.debug("-> createSet");
		Player player1 = createPlayer();
		Player player2 = createPlayer();
		Set set = setService.save(new Set(player1, player2));
		LOG.debug("<- createSet : " + set);
		return set;
	}

	private void winPointsBy(Player player, Integer nbrToWin, Set set) throws MyBusinessException {
		for (int i = 0; i < nbrToWin; i++) {
			playerService.winAPoint(player);
			setService.calculateScore(set);
		}
	}

	private void winGameBy(Player player, Set set) throws MyBusinessException {
		winPointsBy(player, 4, set);
	}

	private void winGamesBy(Player player, Integer nbrToWin, Set set) throws MyBusinessException {
		for (int i = 0; i < nbrToWin; i++) {
			winGameBy(player, set);
		}
	}

	private void winTieBreakGame(Player player, Set set) throws MyBusinessException {
		for (int i = 0; i < 7; i++) {
			playerService.winAPoint(player);
			setService.calculateScore(set);
		}
	}
}
