package com.tennisKata.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tennisKata.config.AppConfigTest;
import com.tennisKata.models.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfigTest.class)
public class PlayerServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(PlayerServiceTest.class);
	
	@Autowired
	private PlayerService playerService ;

	@Test
	public void testSavePlayer(){
		LOG.debug("-> testSavePlayer");
		//when
	    Player entity = buildPlayer();
		Player player = playerService.save(entity);
		
		//then
		assertNotNull(player);
		assertNotNull(player.getId());
		assertNotSame(0, player.getId());
		LOG.debug("<- testSavePlayer");
		
	}
	
	@Test 
	public void testFindPlayerById(){
		LOG.debug("-> testFindPlayerById");
		//when
	    Player entity = buildPlayer();
		Player player = playerService.save(entity);
		
		//then
		Player found = playerService.findById(player.getId());
		assertNotNull(found);
		assertEquals(player, found);
		LOG.debug("<- testFindPlayerById");
	}
	
	@Test
	public void scoresShouldBe0WhenInstantiatingANewPlayer() {

		LOG.debug("-> scoreShouldBe0WhenInstantiatingANewPlayer");
		// Given
		 Player player = buildPlayer();
		final Integer zero = 0;
		
		// When
		player = playerService.save(player);

		// Then
		assertEquals(zero, player.getPointsScore());
		assertEquals(zero, player.getGamesScore());
		assertEquals(zero, player.getSetsScore());
		LOG.debug("<- scoreShouldBe0WhenInstantiatingANewPlayer :" + player);

	}

	@Test
	public void pointScoreShouldBeIncrementedBy1WhenAPlayerWinAPoint() {

		LOG.debug("-> pointScoreShouldBeIncrementedBy1WhenAPlayerWinAPoint");
		// Given
		 Player player = buildPlayer();
		Integer currentScore = Integer.valueOf(RandomStringUtils.randomNumeric(2));

		player.setPointsScore(currentScore);
		player = playerService.save(player);
		assertEquals(currentScore, player.getPointsScore());

		// When
		playerService.winAPoint(player);

		// Then
		Player entity = playerService.findById(player.getId());
		Integer newScore = currentScore + 1;
		assertEquals(newScore, entity.getPointsScore());
		LOG.debug("currentScore = "+currentScore+" ,newScore = "+newScore);
		LOG.debug("<- pointScoreShouldBeIncrementedBy1WhenAPlayerWinAPoint :" + entity);

	}
	
	@Test
	public void gamesScoreShouldBeIncrementedBy1WhenAPlayerWinAGame() {

		LOG.debug("-> gamesScoreShouldBeIncrementedBy1WhenAPlayerWinAGame");
		// Given
		 Player player = buildPlayer();
		Integer currentScore = Integer.valueOf(RandomStringUtils.randomNumeric(2));

		player.setGamesScore(currentScore);
		player = playerService.save(player);
		assertEquals(currentScore, player.getGamesScore());

		// When
		playerService.winAGame(player);

		// Then
		Player entity = playerService.findById(player.getId());
		Integer newScore = currentScore + 1;
		assertEquals(newScore, entity.getGamesScore());
		LOG.debug("currentScore = "+currentScore+" ,newScore = "+newScore);
		LOG.debug("<- gamesScoreShouldBeIncrementedBy1WhenAPlayerWinAGame :" + entity);

	}

	@Test
	public void setsScoreShouldBeIncrementedBy1WhenAPlayerWinASet() {

		LOG.debug("-> setsScoreShouldBeIncrementedBy1WhenAPlayerWinASet");
		// Given
		 Player player = buildPlayer();
		Integer currentScore = Integer.valueOf(RandomStringUtils.randomNumeric(2));

		player.setSetsScore(currentScore);
		player = playerService.save(player);
		assertEquals(currentScore, player.getSetsScore());

		// When
		playerService.winASet(player);

		// Then
		Player entity = playerService.findById(player.getId());
		Integer newScore = currentScore + 1;
		assertEquals(newScore, entity.getSetsScore());
		LOG.debug("currentScore = "+currentScore+" ,newScore = "+newScore);
		LOG.debug("<- setsScoreShouldBeIncrementedBy1WhenAPlayerWinASet :" + entity);

	}
	// Utils
	private Player buildPlayer() {
		LOG.debug("-> createPlayer");
		String name = RandomStringUtils.randomAlphanumeric(20);
		Player player = new Player(name);
		LOG.debug("<- createPlayer : " + player.getName());
		return player;

	}
	
	
}
