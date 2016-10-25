package com.tennisKata.dao;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tennisKata.config.AppConfigTest;
import com.tennisKata.models.Player;
import com.tennisKata.models.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfigTest.class)
public class SetDAOTest extends AbstractDAOTest<Set, SetDAO>{

	@Autowired
	private PlayerDAO playerDao ;
	
	// Utils
	private Player createPlayer() {
		
		String name = RandomStringUtils.randomAlphanumeric(20);
		Player player = playerDao.save(new Player(name));
		
		return player;

	}

	// Api
	@Override
	protected Set buildEntity() {
		
		Player player1 = createPlayer();
		Player player2 = createPlayer();
		Set set = new Set(player1, player2);
		return set;
	}
}
