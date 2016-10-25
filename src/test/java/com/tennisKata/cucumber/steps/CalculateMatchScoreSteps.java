package com.tennisKata.cucumber.steps;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.tennisKata.config.AppConfigTest;
import com.tennisKata.models.Match;
import com.tennisKata.models.Player;
import com.tennisKata.services.MatchService;
import com.tennisKata.services.PlayerService;
import com.tennisKata.utils.TennisConstants;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration(classes = { AppConfigTest.class })
public class CalculateMatchScoreSteps {
	@Autowired
	private PlayerService playerService;

	@Autowired
	private MatchService matchService;

	@Given("^match with id '(\\d+)', firstPlayer set with id '(\\d+)' and secondPlayer set with id '(\\d+)'$")
	public void match_with_firstPlayer_and_second_player(final Integer idMatch, final Integer idPlayer1,
			final Integer idPlayer2) {

		Player player1 = new Player();
		player1.setId(idPlayer1);
		player1 = playerService.save(player1);

		Player player2 = new Player();
		player2.setId(idPlayer2);
		player2 = playerService.save(player2);

		Match match = new Match(player1, player2);
		match.setId(idMatch);
		match = matchService.save(match);

	}

	@Transactional
	@When("^player with id '(\\d+)' win '(\\d+)' sets and '(\\d+)' points and player with id '(\\d+)' win '(\\d+)' sets and '(\\d+)' points in match with id '(\\d+)'$")
	public void win_points_by_players(final int idPlayer1, final int nbrSets1, final int nbrPoints1, final int idPlayer2, final int nbrSets2,
			final int nbrPoints2, final int setId) throws Exception {

		Match match = matchService.findById(setId);

		Player player1 = playerService.findById(idPlayer1);
		for (int i = 0; i < nbrSets1; i++) {
			// win 6 games to win a set
			for (int k = 0; k < TennisConstants.NUMBER_OF_GAMES_TO_WIN_SET; k++) {
				// win 4 points to win a game
				for (int j = 0; j < TennisConstants.NUMBER_OF_POINTS_TO_WIN_GAME; j++) {
					playerService.winAPoint(player1, match);
				}
			}
		}

		Player player2 = playerService.findById(idPlayer2);
		for (int i = 0; i < nbrSets2; i++) {
			// win 6 games to win a set
			for (int k = 0; k < TennisConstants.NUMBER_OF_GAMES_TO_WIN_SET; k++) {
				// win 4 points to win a game
				for (int j = 0; j < TennisConstants.NUMBER_OF_POINTS_TO_WIN_GAME; j++) {
					playerService.winAPoint(player2, match);
				}
			}
		}
			
			for(int i = 0; i < nbrPoints1; i++){
				playerService.winAPoint(player1, match);
			}

			for(int i = 0; i < nbrPoints2; i++){
				playerService.winAPoint(player2, match);
			}

	}

	@Then("^score of match with id '(\\d+)' is '(.*)'$")
	public void get_score_of_match(final int matchId, final String score) {
		Match match = matchService.findById(matchId);
		assertEquals(score, match.getScore());

	}

}
