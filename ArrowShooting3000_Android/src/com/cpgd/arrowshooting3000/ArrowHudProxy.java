package com.cpgd.arrowshooting3000;

import org.andengine.entity.sprite.Sprite;

public class ArrowHudProxy {

	private long objref;
	private Sprite bgSprite;
	private Sprite overlaySprite;
	
	private native long arrowHud(float spriteWidth);
	private native float getCurrentWidth(float strength, long objref);
	
	public ArrowHudProxy(float spriteWidth) {
		objref = arrowHud(spriteWidth);
	}
	
	public float getCurrentWidth(float strength) {
		return getCurrentWidth(strength, objref);
	}
	
	public Sprite getBgSprite() {
		return bgSprite;
	}
	
	public void setBgSprite(Sprite bgSprite) {
		this.bgSprite = bgSprite;
	}
	
	public Sprite getOverlaySprite() {
		return overlaySprite;
	}
	
	public void setOverlaySprite(Sprite overlaySprite) {
		this.overlaySprite = overlaySprite;
	}
	
}
