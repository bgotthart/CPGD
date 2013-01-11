package com.cpgd.arrowshooting3000.components;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class ArrowProxy extends ImageObject{
	
private long ref;
	
	private native long arrowProxy(int x, int y);
	private native int getPositionX(long ref);
	private native int getPositionY(long ref);
	private native float getStrength(long ref);
	private native long setPositionX(float x, long ref);
	private native long setPositionY(float y, long ref);
	private native long startArrow(long ref);
	private native long shootArrow(int mouseX, int mouseY, long ref);
	private native long update(float delta, long ref);
	private native float getRotation(long ref);
	private native float getTouchRotation(float mouseX, float mouseY, long ref);
	
	public ArrowProxy(Image image, Vector2f position) {
		super(image, position);
		this.ref = arrowProxy((int)position.x, (int)position.y);
	}
	
	public Vector2f getPosition(){
		return new Vector2f((float)this.getPositionX(), (float)this.getPositionY());
	}
	
	public int getPositionX(){
		return getPositionX(ref);
	}
	
	public int getPositionY(){
		return getPositionY(ref);
	}
	
	public float getStrength(){
		return getStrength(ref);
	}
	
	public void setPositionX(float x){
		ref = setPositionX(x, ref);
		this.setPosition(new Vector2f(x, this.position.y));

	}
	
	public void setPositionY(float y){
		ref = setPositionY(y, ref);
		this.setPosition(new Vector2f(this.position.x, y));
	}
	
	public void startArrow(){
		ref = startArrow(ref);
	}
	
	public void shootArrow(int mouseX, int mouseY){
		ref = shootArrow(mouseX, mouseY, ref);
	}
	
	public float getRotation(){
		return getRotation(ref);
	}
	
	public float getTouchRotation(int mouseX, int mouseY){
		return getTouchRotation((float)mouseX, (float)mouseY, ref);
	}
	
	public void update(float delta){
		ref = update(delta, ref);
		position.x = this.getPositionX();
		position.y = this.getPositionY();
		this.image.setRotation((float)Math.toDegrees(this.getRotation()));
	}

}
