  Feature: calculate match score
  	
  
  Scenario: calculate score of a match
  
    Given match with id '3', firstPlayer set with id '1' and secondPlayer set with id '2'
	When player with id '1' win '1' sets and '3' points and player with id '2' win '1' sets and '3' points in match with id '3'
	Then score of match with id '3' is 'Game [DEUCE], Set [0 - 0], Match [1 - 1]'