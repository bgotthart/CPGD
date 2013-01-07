package com.arrowshooting.gameplay.level;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class PlayerWrapper extends ImageObject{
	
	private long ref;
	
	private native long playerWrapper(int x, int y);
	private native int getPositionX(long l);
	private native int getPositionY(long l);

	public PlayerWrapper(Image image, Vector2f position) {
		super(image, position);
		ref = playerWrapper((int)position.x, (int)position.y);
	}
	
	public int getPositionX(){
		return getPositionX(ref);
	}
	
	public int getPositionY(){
		return getPositionY(ref);
	}
	
	public Vector2f getPosition(){
		return new Vector2f((float)this.getPositionX(), (float)this.getPositionY());
	}
}
