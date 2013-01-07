package com.arrowshooting.gameplay.level;

public class ScoreWrapper {
	private long ref;
	
	private native long scoreWrapper();
	private native int getScore(long ref);
	
	public ScoreWrapper(){
		ref = scoreWrapper();
	}
	
	public int getScore(){
		return getScore(ref);
	}

}
