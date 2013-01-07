package com.arrowshooting.gameplay.level;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class ArrowWrapper extends ImageObject{
	
	private long ref;
	
	private native long arrowWrapper(int x, int y);
	private native int getPositionX(long ref);
	private native int getPositionY(long ref);
	private native float getStrength(long ref);
	private native long setPositionX(float x, long ref);
	private native long setPositionY(float y, long ref);
	private native long startArrow(long ref);
	private native long shootArrow(int mouseX, int mouseY, long ref);
	private native long update(long ref);
	private native float getRotation(long ref);
	private native float getTouchRotation(float mouseX, float mouseY, long ref);
	
	public ArrowWrapper(Image image, Vector2f position) {
		super(image, position);
		this.ref = arrowWrapper((int)position.x, (int)position.y);
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
	}
	
	public void setPositionY(float y){
		ref = setPositionY(y, ref);
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
	
	public void update(){
		ref = update(ref);
		position.x = this.getPositionX();
		position.y = this.getPositionY();
		this.image.setRotation((float)Math.toDegrees(this.getRotation()));
	}

}
