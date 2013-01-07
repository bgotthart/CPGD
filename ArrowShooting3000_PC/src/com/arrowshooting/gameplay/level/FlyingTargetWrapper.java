package com.arrowshooting.gameplay.level;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class FlyingTargetWrapper extends TargetWrapper {
	
	private native long flyingTargetWrapper(int x, int y, int width, int height);
	private native long update(long ref);	
	private native int collidesWith(int arrowX, int arrowY, long ref);
	
	public FlyingTargetWrapper(Image image, Vector2f position) {
		super(image, position);
		ref = flyingTargetWrapper((int)position.x, (int)position.y, image.getWidth(), image.getHeight());
	}
	
	public Vector2f getPosition(){
		return new Vector2f((float)this.getPositionX(), (float)this.getPositionY());
	}
	
	public void update(){
		ref = update(ref);
		position.x = this.getPositionX();
	}
	
	public int collidesWith(int arrowX, int arrowY){
		return collidesWith(arrowX, arrowY, ref);
	}

}
