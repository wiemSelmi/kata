package com.tennisKata.dao;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tennisKata.config.AppConfigTest;
import com.tennisKata.models.Match;
import com.tennisKata.models.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfigTest.class)
public class MatchDAOTest extends AbstractDAOTest<Match, MatchDAO> {

	@Autowired
	private PlayerDAO playerDao;

	// Utils
	private Player createPlayer() {

		String name = RandomStringUtils.randomAlphanumeric(20);
		Player player = playerDao.save(new Player(name));

		return player;
	}

	// Api
	@Override
	protected Match buildEntity() {
		Player player1 = createPlayer();
		Player player2 = createPlayer();
		Match match = new Match(player1, player2);

		return match;
	}

}
