package com.arrowshooting.gameplay.level;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class TargetWrapper extends ImageObject {

	protected long ref;
	
	private native long targetWrapper(int x, int y, int width, int height);
	private native int getPositionX(long l);
	private native int getPositionY(long l);
	private native long setPositionX(float x, long l);
	private native long setPositionY(float y, long l);
	private native long update(long ref);
	private native int collidesWith(int arrowX, int arrowY, long ref);
	
	public TargetWrapper(Image image, Vector2f position) {
		super(image, position);
		this.ref = targetWrapper((int)position.x, (int)position.y, this.image.getWidth(), this.image.getHeight());
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
	
	public void setPositionX(float x){
		ref = setPositionX(x, ref);
	}
	
	public void setPositionY(float y){
		ref = setPositionY(y, ref);
	}
	
	public void update(){
		this.ref = update(ref);
//		position.x = this.getPositionX();
//		position.y = this.getPositionY();
	}
	
	public int collidesWith(int arrowX, int arrowY){
		return collidesWith(arrowX, arrowY, ref);
	}

}
