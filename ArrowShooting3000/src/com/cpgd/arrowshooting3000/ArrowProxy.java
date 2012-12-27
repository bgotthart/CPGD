package com.cpgd.arrowshooting3000;

public class ArrowProxy {

	private long objref;
	
	private native long arrowProxy(int x, int y);
	private native int getPositionX(long l);
	private native int getPositionY(long l);
	private native long shootArrow(int mouseX, int mouseY, float strength, long objref);
	private native long update(long objref);
	
	public ArrowProxy(int x, int y) {
		objref = arrowProxy(x, y);
	}
	
	public void shootArrow(int mouseX, int mouseY, float strength) {
		objref = shootArrow(mouseX, mouseY, strength, objref);
	}
	
	public void update() {
		objref = update(objref);
	}
	
	public int getPositionX() {
		return getPositionX(objref);
	}
	
	public int getPositionY() {
		return getPositionY(objref);
	}
	
}
