package com.cpgd.arrowshooting3000;

import org.andengine.entity.sprite.Sprite;

public class GroundTargetProxy {
	
	private long objref;
	private Sprite sprite;
	
	private native long groundTargetProxy(int x, int y);
	private native int getPositionX(long l);
	private native int getPositionY(long l);
	private native long update(long objref);
	
	public GroundTargetProxy(int x, int y) {
		objref = groundTargetProxy(x, y);
	}
	
	public void update() {
		objref = update(objref);
		
		sprite.setPosition(this.getPositionX(), this.getPositionY());
	}
	
	public int getPositionX() {
		return getPositionX(objref);
	}
	
	public int getPositionY() {
		return getPositionY(objref);
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Sprite getSprite() {
		return sprite;
	}

}
