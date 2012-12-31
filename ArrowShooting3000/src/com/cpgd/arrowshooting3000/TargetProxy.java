package com.cpgd.arrowshooting3000;

import org.andengine.entity.sprite.Sprite;

public class TargetProxy {
	
	protected long objref;
	protected Sprite sprite;
	
	private native long targetProxy(int x, int y, int width, int height);
	private native int getPositionX(long l);
	private native int getPositionY(long l);
	private native long update(long objref);
	private native int collidesWith(int arrowX, int arrowY, long objref);
	
	public TargetProxy() {
		objref = 0;
	}
	
	public TargetProxy(Sprite spr) {
		this.sprite = spr;
		objref = targetProxy((int)sprite.getX(), (int)sprite.getY(), (int)sprite.getWidth(), (int)sprite.getHeight());
	}
	
	public void update() {
		objref = update(objref);
		sprite.setPosition(this.getPositionX(), this.getPositionY());
	}
	
	public int collidesWith(int arrowX, int arrowY) {
		return collidesWith(arrowX, arrowY, objref);
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
