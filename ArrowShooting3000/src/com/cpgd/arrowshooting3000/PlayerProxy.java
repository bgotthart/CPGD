package com.cpgd.arrowshooting3000;

import org.andengine.entity.sprite.Sprite;

public class PlayerProxy {
	
	private long objref;
	private Sprite sprite;
	
	private native long playerProxy(int x, int y);
	private native int getPositionX(long l);
	private native int getPositionY(long l);
	
	public PlayerProxy(int x, int y) {
		objref = playerProxy(x, y);
	}
	
	public int getPositionX() {
		return getPositionX(objref);
	}
	
	public int getPositionY() {
		return getPositionY(objref);
	}
	
	public String getPosition() {
		return "Player - x: "+getPositionX(objref)+", y: "+getPositionY(objref);
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
}
