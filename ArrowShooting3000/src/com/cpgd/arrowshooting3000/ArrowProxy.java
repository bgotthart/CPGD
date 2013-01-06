package com.cpgd.arrowshooting3000;

import org.andengine.entity.sprite.Sprite;
import org.andengine.util.debug.Debug;

public class ArrowProxy {

	private long objref;
	private Sprite sprite;
	
	private native long arrowProxy(int x, int y);
	private native int getPositionX(long l);
	private native int getPositionY(long l);
	private native float getRotation(long l);
	private native float getTouchRotation(float mouseX, float mouseY, long objref);
	private native long startArrow(long l);
	private native long shootArrow(int mouseX, int mouseY, long objref);
	private native long update(long objref);
	
	public ArrowProxy(int x, int y) {
		objref = arrowProxy(x, y);
	}
	
	public void startArrow() {
		objref = startArrow(objref);
	}
	
	public void shootArrow(int mouseX, int mouseY) {
		objref = shootArrow(mouseX, mouseY, objref);
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
	
	public float getTouchRotation(float mouseX, float mouseY) {
		return getTouchRotation(mouseX, mouseY, objref);
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
}
