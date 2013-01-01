package com.cpgd.arrowshooting3000;

import org.andengine.entity.sprite.Sprite;

public class ArrowProxy {

	private long objref;
	private Sprite sprite;
	
	private native long arrowProxy(int x, int y);
	private native int getPositionX(long l);
	private native int getPositionY(long l);
	private native float getRotation(long l);
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
		
		sprite.setPosition(this.getPositionX(), this.getPositionY());
		sprite.setRotation((float)Math.toDegrees(this.getRotation()));
	}
	
	public int getPositionX() {
		return getPositionX(objref);
	}
	
	public int getPositionY() {
		return getPositionY(objref);
	}
	
	public float getRotation() {
		return getRotation(objref);
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
}