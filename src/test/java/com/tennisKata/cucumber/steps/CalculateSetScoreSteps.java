package com.tennisKata.cucumber.steps;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.tennisKata.config.AppConfigTest;
import com.tennisKata.models.Player;
import com.tennisKata.models.Set;
import com.tennisKata.services.PlayerService;
import com.tennisKata.services.SetService;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration(classes = { AppConfigTest.class })
public class CalculateSetScoreSteps {
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private SetService setService;


	@Given("^set with id '(\\d+)', firstPlayer set with id '(\\d+)' and secondPlayer set with id '(\\d+)'$")
	public void game_with_firstPlayer_and_second_player(final Integer idSet, final Integer idPlayer1,
			final Integer idPlayer2) {
		Player player1 = new Player();
		player1.setId(idPlayer1);
		player1 = playerService.save(player1);

		Player player2 = new Player();
		player2.setId(idPlayer2);
		player2 = playerService.save(player2);

		Set set = new Set(player1, player2);
		set.setId(idSet);
		set = setService.save(set);

	}

	@Transactional
	@When("^player with id '(\\d+)' win '(\\d+)' games and player with id '(\\d+)' win '(\\d+)' games and calculate score set with id '(\\d+)'$")
	public void win_points_and_calculate_score(final int idPlayer1, final int nbrGames1, final int idPlayer2,
			final int nbrGames2, final int setId) throws Exception {

		Set set = setService.findById(setId);

		Player player1 = playerService.findById(idPlayer1);
		for (int i = 0; i < nbrGames1; i++) {
			for (int j = 0; j < 4; j++) {
				playerService.winAPoint(player1);
				setService.calculateScore(set);
			}
		}

		Player player2 = playerService.findById(idPlayer2);
		for (int i = 0; i < nbrGames2; i++) {
			// win a game

			for (int j = 0; j < 4; j++) {
				playerService.winAPoint(player2);
				setService.calculateScore(set);
			}

		}

	}

	@Then("^score of set with id '(\\d+)' is '(.*)'$")
	public void get_score_of_set(final int setId, final String score) {
		Set set = setService.findById(setId);
		assertEquals(score, set.getScore());

	}
}
