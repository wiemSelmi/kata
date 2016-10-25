package com.tennisKata.dao;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tennisKata.config.AppConfigTest;
import com.tennisKata.models.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfigTest.class)
public class PlayerDAOTest extends AbstractDAOTest<Player, PlayerDAO> {

	// API
	@Override
	protected Player buildEntity() {

		String name = RandomStringUtils.randomAlphanumeric(20);
		Player player = new Player(name);

		return player;
	}
}
