package com.example.testjni3;

public class PlayerProxy {

	static {
		System.loadLibrary("TestJNI3");
	}
	
	private native int getPositionX();
	
	public String getPosition() {
		return ""+getPositionX();
	}
	
}
