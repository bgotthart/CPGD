package com.cpgd.arrowshooting3000;

public class ScoreProxy {
	
	private native void score();
	private native int getScore();
	private native void resetScore();
	
	public ScoreProxy() {
		score();
	}
	
	public int getCurrentScore() {
		return getScore();
	}
	
	public void resetCurrentScore() {
		resetScore();
	}
	
}
