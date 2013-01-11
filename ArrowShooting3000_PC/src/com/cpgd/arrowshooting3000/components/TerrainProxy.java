package com.cpgd.arrowshooting3000.components;

import org.newdawn.slick.geom.Vector2f;

public class TerrainProxy {
	
private long ref;
	
	private native long terrainProxy(int width, int heigth, int x, int y);
	private native float getRandomTargetPositionX(float targetWidth, float targetHeigth, long ref);
	private native float getRandomTargetPositionY(float targetWidth, float targetHeigth, long ref);
	private native long getRandomFlyingTargetPositionX(float targetWidth, float targetHeigth, long ref);
	private native long getRandomFlyingTargetPositionY(float targetWidth, float targetHeigth, long ref);
	
	
	public TerrainProxy(int width, int heigth, int playerX, int playerY){
		this.ref = terrainProxy(width, heigth, playerX, playerY);
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
