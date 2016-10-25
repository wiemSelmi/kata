  Feature: calculate set score
  	
  
  Scenario: calculate score of a set
  
    Given set with id '3', firstPlayer set with id '1' and secondPlayer set with id '2'
	When player with id '1' win '5' games and player with id '2' win '6' games and calculate score set with id '3'
	Then score of set with id '3' is '5 - 6'