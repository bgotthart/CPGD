package com.arrowshooting.gameplay.level;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class ArrowHudWrapper extends ImageObject {
	
	private long ref;
	private float hudWidth;
	private float hudHeigth;
	private Rectangle shape;
	private Rectangle overlayShape;
	
	private native long arrowHudWrapper(float hudWidth);
	private native float getCurrentWidth(float strength, long ref);
	private native float getCurrentScale(float strength, long ref);

	public ArrowHudWrapper(Image image, Vector2f position, float hudWidth, float hudHeigth) {
		super(image, position);
		this.hudWidth = hudWidth;
		this.hudHeigth = hudHeigth;
		
		ref = arrowHudWrapper(this.hudWidth);
		
		this.shape = new Rectangle(position.x, position.y, this.hudWidth, this.hudHeigth);	
		this.overlayShape = new Rectangle(position.x, position.y, this.hudWidth, this.hudHeigth);
	}
	
	public float getCurrentWidth(float strength){
		return getCurrentWidth(strength, ref);
	}
	
	public float getCurrentScale(float strength){
		return getCurrentScale(strength, ref);
	}
	
	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.red);
		graphics.fill(shape);
		System.out.println(shape.getWidth());
		graphics.setColor(Color.green);
		graphics.fill(overlayShape);
		graphics.setColor(Color.white);
	}
	
	public void setOverlayWidth(float width){
		overlayShape.setWidth(width);
	}
	
}
