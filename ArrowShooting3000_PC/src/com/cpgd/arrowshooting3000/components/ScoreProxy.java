package com.cpgd.arrowshooting3000.components;

public class ScoreProxy {
	
	private long ref;
	
	private native long scoreProxy();
	private native int getScore(long ref);
	private native long resetScore(long ref);
	
	public ScoreProxy(){
		ref = scoreProxy();
	}
	
	public int getScore(){
		return getScore(ref);
	}
	
	public void resetScore(){
		resetScore(ref);
	}

}
