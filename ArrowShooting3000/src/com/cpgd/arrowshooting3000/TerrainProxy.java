package com.cpgd.arrowshooting3000;

public class TerrainProxy {

	private long objref;
	
	private native long terrain(int width, int height);
	private native float GetRandomTargetPositionX(float targetWidth, float targetHeight, long objref);
	private native float GetRandomTargetPositionY(float targetWidth, float targetHeight, long objref);
	private native float GetRandomFlyingStartPositionX(float targetWidth, float targetHeight, long objref);
	private native float GetRandomFlyingStartPositionY(float targetWidth, float targetHeight, long objref);
	
	public TerrainProxy(int width, int height) {
		objref = terrain(width, height);
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
	
}
