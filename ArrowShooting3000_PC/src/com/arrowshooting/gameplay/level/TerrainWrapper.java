package com.arrowshooting.gameplay.level;

import org.newdawn.slick.geom.Vector2f;

public class TerrainWrapper {
	
	private long ref;
	
	private native long terrainWrapper(int width, int heigth);
	private native float getRandomTargetPositionX(float targetWidth, float targetHeigth, long ref);
	private native float getRandomTargetPositionY(float targetWidth, float targetHeigth, long ref);
	private native long getRandomFlyingTargetPositionX(float targetWidth, float targetHeigth, long ref);
	private native long getRandomFlyingTargetPositionY(float targetWidth, float targetHeigth, long ref);
	
	
	public TerrainWrapper(int width, int heigth){
		this.ref = terrainWrapper(width, heigth);
	}
	
	public Vector2f getRandomTargetPosition(float targetWidth, float targetHeigth){
		return new Vector2f(
				getRandomTargetPositionX(targetWidth, targetHeigth, ref), 
				getRandomTargetPositionY(targetWidth, targetHeigth, ref));
	}
	
	public Vector2f getRandomFlyingTargetPosition(float targetWidth, float targetHeigth){
		return new Vector2f(
				getRandomFlyingTargetPositionX(targetWidth, targetHeigth, ref), 
				getRandomFlyingTargetPositionY(targetWidth, targetHeigth, ref));
	}

}
