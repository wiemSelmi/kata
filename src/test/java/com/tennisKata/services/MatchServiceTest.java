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
import com.tennisKata.models.Match;
import com.tennisKata.models.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfigTest.class)
public class MatchServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(MatchServiceTest.class);

	@Autowired
	private MatchService matchService;
	@Autowired
	private PlayerService playerService;

	// Tests
	@Test
	public void matchShouldStartBy0ForScore() {
		LOG.debug("-> matchShouldStartBy0ForScore");
		// Given
		final Match match = createMatch();

		// Then
		assertEquals("Game [LOVE - LOVE], Set [0 - 0], Match [0 - 0]", match.getScore());
		LOG.debug("<- matchShouldStartBy0ForScore : " + match.getScore());
	}

	@Test
	public void matchShouldStartWithNoWinner() {
		LOG.debug("-> matchShouldStartWithNoWinner");
		// Given
		final Match match = createMatch();

		// Then
		assertNull(match.getWinner());
		LOG.debug("<- matchShouldStartWithNoWinner");
	}

	@Test
	public void matchScoreShouldBeOneOneWhenEachPlayerWon1Set() throws MyBusinessException {
		LOG.debug("-> matchScoreShouldBeOneOneWhenEachPlayerWon1Set");
		// Given
		Match match = createMatch();

		//
		final Player player1 = match.getFirstPlayer();
		winSetBy(player1, match);

		final Player player2 = match.getSecondPlayer();
		winSetBy(player2, match);

		// Then
		match = matchService.findById(match.getId());
		final String expectedScore = "Game [" + player2.getName() + " WON], Set [" + player2.getName()
				+ " WON], Match [1 - 1]";
		assertEquals(expectedScore, match.getScore());
		assertNull(match.getWinner());
		LOG.debug("<- matchScoreShouldBeOneOneWhenEachPlayerWon1Set, score = " + match.getScore());
	}

	@Test
	public void matchShouldBeWonByThePlayerWhoWonAt2Sets() throws MyBusinessException {
		LOG.debug("-> matchShouldBeWonByThePlayerWhoWonAt2Sets");
		// Given
		Match match = createMatch();

		//
		final Player player1 = match.getFirstPlayer();
		winSetBy(player1, match);
		winSetBy(player1, match);

		// Then
		match = matchService.findById(match.getId());
		String expectedScore = "Game, set, match " + player1.getName();
		assertEquals(expectedScore, match.getScore());
		assertNotNull(match.getWinner());
		assertEquals(player1, match.getWinner());
		LOG.debug("<- matchShouldBeWonByThePlayerWhoWonAt2Sets, score = " + match.getScore());
	}

	// Utils
	private Player createPlayer() {
		LOG.debug("-> createPlayer");
		String name = RandomStringUtils.randomAlphanumeric(20);
		Player player = playerService.save(new Player(name));
		LOG.debug("<- createPlayer : " + player.getName());
		return player;

	}

	private Match createMatch() {
		LOG.debug("-> createMatch");
		Player player1 = createPlayer();
		Player player2 = createPlayer();
		Match match = matchService.save(new Match(player1, player2));
		LOG.debug("<- createMatch : " + match);
		return match;
	}

	private void winPointsBy(Player player, Integer nbrToWin, Match match) throws MyBusinessException {
		for (int i = 0; i < nbrToWin; i++) {
			playerService.winAPoint(player, match);

		}
	}

	private void winGameBy(Player player, Match match) throws MyBusinessException {
		winPointsBy(player, 4, match);
	}

	private void winGamesBy(Player player, Integer nbrToWin, Match match) throws MyBusinessException {
		for (int i = 0; i < nbrToWin; i++) {
			winGameBy(player, match);
		}
	}

	private void winSetBy(Player player, Match match) throws MyBusinessException {
		winGamesBy(player, 6, match);
	}
}
