package com.tennisKata.dao;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tennisKata.config.AppConfigTest;
import com.tennisKata.models.Game;
import com.tennisKata.models.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfigTest.class)
public class GameDAOTest extends AbstractDAOTest<Game, GameDAO> {

	@Autowired
	private PlayerDAO playerDao;

	// Utils
	private final Player createPlayer() {

		String name = RandomStringUtils.randomAlphanumeric(20);
		final Player player = playerDao.save(new Player(name));

		return player;

	}

	// Api
	@Override
	protected Game buildEntity() {
		Player player1 = createPlayer();
		Player player2 = createPlayer();
		Game game = new Game(player1, player2);

		return game;
	}
}
