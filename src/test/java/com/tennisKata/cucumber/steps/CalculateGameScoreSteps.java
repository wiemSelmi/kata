package com.tennisKata.cucumber.steps;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.tennisKata.config.AppConfigTest;
import com.tennisKata.models.Game;
import com.tennisKata.models.Player;
import com.tennisKata.services.GameService;
import com.tennisKata.services.PlayerService;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration(classes = { AppConfigTest.class })
public class CalculateGameScoreSteps {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private GameService gameService;

	@Given("^game with id '(\\d+)', firstPlayer set with id '(\\d+)' and secondPlayer set with id '(\\d+)'$")
	public void game_with_firstPlayer_and_second_player(final Integer idGame, final Integer idPlayer1,
			final Integer idPlayer2) {
		Player player1 = new Player();
		player1.setId(idPlayer1);
		player1 = playerService.save(player1);

		Player player2 = new Player();
		player2.setId(idPlayer2);
		player2 = playerService.save(player2);

		Game game = new Game(player1, player2);
		game.setId(idGame);
		gameService.save(game);

	}

	@When("^player with id '(\\d+)' win '(\\d+)' points and player with id '(\\d+)' win '(\\d+)' points and calculate score game with id '(\\d+)'$")
	public void win_points_and_calculate_score(final int idPlayer1, final int nbrPoints1, final int idPlayer2,
			final int nbrPoints2, final int gameId) throws Exception {
		Player player1 = playerService.findById(idPlayer1);
		for (int i = 0; i < nbrPoints1; i++) {
			playerService.winAPoint(player1);
		}

		Player player2 = playerService.findById(idPlayer2);
		for (int i = 0; i < nbrPoints2; i++) {
			playerService.winAPoint(player2);
		}
		
		Game game = gameService.findById(gameId);
		gameService.calculateScore(game);
	}

	@Then("^score of game with id '(\\d+)' is '(.*)'$")
	public void get_score_of_game(final int gameId, final String score) {
		Game game = gameService.findById(gameId);
		assertEquals(score, game.getScore());

	}

}
