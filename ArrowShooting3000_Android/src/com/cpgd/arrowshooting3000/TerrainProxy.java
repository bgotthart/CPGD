package com.cpgd.arrowshooting3000;

public class TerrainProxy {

	private long objref;
	
	private native long terrain(int width, int height, int playerX, int playerY);
	private native float GetRandomTargetPositionX(float targetWidth, float targetHeight, long objref);
	private native float GetRandomTargetPositionY(float targetWidth, float targetHeight, long objref);
	private native float GetRandomFlyingStartPositionX(float targetWidth, float targetHeight, long objref);
	private native float GetRandomFlyingStartPositionY(float targetWidth, float targetHeight, long objref);
	private native int GetRandomValue(long objref);
	
	public TerrainProxy(int width, int height, int playerX, int playerY) {
		objref = terrain(width, height, playerX, playerY);
	}
	
	public float GetRandomTargetPositionX(float targetWidth, float targetHeight) {
		return GetRandomTargetPositionX(targetWidth, targetHeight, objref);
	}
	
	public float GetRandomTargetPositionY(float targetWidth, float targetHeight) {
		return GetRandomTargetPositionY(targetWidth, targetHeight, objref);
	}
	
	public float GetRandomFlyingStartPositionX(float targetWidth, float targetHeight) {
		return GetRandomFlyingStartPositionX(targetWidth, targetHeight, objref);
	}
	
	public float GetRandomFlyingStartPositionY(float targetWidth, float targetHeight) {
		return GetRandomFlyingStartPositionY(targetWidth, targetHeight, objref);
	}
	
	public int GetRandomValue() {
		return GetRandomValue(objref);
	}
	
}
