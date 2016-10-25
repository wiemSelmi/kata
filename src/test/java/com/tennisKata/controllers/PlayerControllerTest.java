package com.tennisKata.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tennisKata.config.AppConfigTest;
import com.tennisKata.models.Player;
import com.tennisKata.services.PlayerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfigTest.class)
@WebAppConfiguration
public class PlayerControllerTest {

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;

	@Mock
    PlayerService playerServiceMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}

	@Test
	public void testSavePlayer() throws Exception {
		String name1 = RandomStringUtils.randomAlphanumeric(20);
		Player first = new Player(name1);

		String name2 = RandomStringUtils.randomAlphanumeric(20);
		Player second = new Player(name2);
		
		
		when(playerServiceMock.findAll()).thenReturn(Arrays.asList(first, second));

		mockMvc.perform(get("/api/players"))
		.andExpect(status().isOk());
	}
}
