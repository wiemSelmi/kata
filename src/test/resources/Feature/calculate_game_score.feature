  Feature: calculate game score
  	
  
  Scenario: calculate score of a game
  
    Given game with id '3', firstPlayer set with id '1' and secondPlayer set with id '2'
	When player with id '1' win '3' points and player with id '2' win '2' points and calculate score game with id '3'
	Then score of game with id '3' is 'FORTY - THIRTY'