  Feature: calculate tie break game score
  	
  
  Scenario: calculate score of a tie break game
  
    Given tie break game with id '3', firstPlayer set with id '1' and secondPlayer set with id '2'
	When player with id '1' win '5' points and player with id '2' win '6' points and calculate score tie break game with id '3'
	Then score of tie break game with id '3' is '5 - 6'