package com.cpgd.arrowshooting3000.components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.ShapeRenderer;

public class ArrowHudProxy {
	private long ref;
	private Rectangle background;
	private Rectangle overlay;
	private Image backgroundImg;
	private Image overlayImg;
	
	private native long arrowHudProxy(float hudWidth);
	private native float getCurrentWidth(float strength, long ref);
	private native float getCurrentScale(float strength, long ref);

	
	public ArrowHudProxy(Image backgroundImage, Image overlayImg, 
			int x, int y, float hudWidth, float hudHeight) {
		ref = arrowHudProxy(hudWidth);

		this.backgroundImg = backgroundImage;
		this.overlayImg = overlayImg;
		
		this.background = new Rectangle(x, y, hudWidth, hudHeight);
		this.overlay = new Rectangle(x, y, 0, hudHeight);
		
	}
	
	public Rectangle getBackgroundShape(){
		return background;
	}
	
	public Rectangle getOverlayShape(){
		return overlay;
	}
	
	public void setBackgroundShape(Rectangle shape){
		this.background = shape;
	}
	
	public void setOverlayShape(Rectangle shape){
		this.overlay = shape;
	}
	
	public float getCurrentWidth(float strength){
		return getCurrentWidth(strength, ref);
	}
	
	public float getCurrentScale(float strength){
		return getCurrentScale(strength, ref);
	}
	
	public void render(Graphics graphics) {
		ShapeRenderer.textureFit(background, backgroundImg);
		ShapeRenderer.textureFit(overlay, overlayImg);

	}
	
	public void setOverlayWidth(float width){
		overlay.setWidth(width);
	}

}
