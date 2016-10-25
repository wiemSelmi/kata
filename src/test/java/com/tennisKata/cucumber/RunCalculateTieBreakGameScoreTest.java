package com.tennisKata.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/feature/calculate_tie_break_game_score.feature", glue = "com.tennisKata.cucumber.steps")
public class RunCalculateTieBreakGameScoreTest {

}
