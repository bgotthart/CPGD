package com.cpgd.arrowshooting3000.components;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class TargetProxy extends ImageObject{
	
	protected long ref;
	
	private native long targetProxy(int x, int y, int width, int height);
	private native int getPositionX(long l);
	private native int getPositionY(long l);
	private native long setPositionX(float x, long l);
	private native long setPositionY(float y, long l);
	private native long update(long ref);
	private native int collidesWith(int arrowX, int arrowY, long ref);
	
	public TargetProxy(Image image, Vector2f position) {
		super(image, position);
		this.ref = targetProxy((int)position.x, (int)position.y, image.getWidth(), image.getHeight());
	}
	
	public int getPositionX(){
		return getPositionX(ref);
	}
	
	public int getPositionY(){
		return getPositionY(ref);
	}
	
	public void setPositionX(float x){
		ref = setPositionX(x, ref);
		this.setPosition(new Vector2f(x, this.getPositionY()));
	}
	
	public void setPositionY(float y){
		ref = setPositionY(y, ref);
		this.setPosition(new Vector2f(this.getPositionX(), y));
	}
	
	public void update(){
		this.ref = update(ref);
	}
	
	public int collidesWith(int arrowX, int arrowY){
		return collidesWith(arrowX, arrowY, ref);
	}


}
