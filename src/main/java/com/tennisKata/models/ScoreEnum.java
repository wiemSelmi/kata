package com.tennisKata.models;

public enum ScoreEnum {
	LOVE(0), FIFTEEN(1), THIRTY(2), FORTY(3), DEUCE(), ADVANTAGE(), WON();
	private int val;

	private ScoreEnum() {

	}

	private ScoreEnum(int val) {
		this.val = val;
	}
	
	public int getVal(){
        return val; 
    }
	
	public static ScoreEnum getScore(int val){
		ScoreEnum score = null ;
		switch (val) {
		case 0 :
			score = LOVE;
			break;
		case 1 :
			score = FIFTEEN ;
			break;
		case 2 : 
			score = THIRTY ;
			break ;
		case 3 : 
			score = FORTY ;
			break;
		default :
			break;	
		}
		return score ;
	}
}
