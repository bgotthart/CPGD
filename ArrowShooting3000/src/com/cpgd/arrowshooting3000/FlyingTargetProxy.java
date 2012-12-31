package com.cpgd.arrowshooting3000;

import org.andengine.entity.sprite.Sprite;

public class FlyingTargetProxy extends TargetProxy {

	private native long flyingTargetProxy(int x, int y, int width, int height);
	private native long update(long objref);
	
	public FlyingTargetProxy(Sprite spr) {
		this.sprite = spr;
		objref = flyingTargetProxy((int)sprite.getX(), (int)sprite.getY(), (int)sprite.getWidth(), (int)sprite.getHeight());
	}
	
	public void update() {
		objref = update(objref);
		sprite.setPosition(this.getPositionX(), this.getPositionY());
	}

}
