package com.cpgd.arrowshooting3000;

public class ScoreProxy {

	private long objref;
	
	private native long score();
	private native int getScore(long objref);
	
	public ScoreProxy() {
		objref = score();
	}
	
	public int getScore() {
		return getScore(objref);
	}
	
}
