#ifndef TERRAIN_H
#define TERRAIN_H

#include "vector.h"

class Terrain{
public:
	int width;
	int height;
	int playerX;
	int playerY;

private:
	int alphamap[0];
	int calcRandomX(float);
	int calcRandomY(float);
	int calcRandomYiOS(float);
	
public:
	Terrain(int, int, int, int);
	void GenerateTerrain();
	Vector* GetRandomTargetPosition(float, float);
	Vector* GetRandomTargetPositionIOS(float targetWidth, float targetHeight);
	Vector* GetRandomFlyingStartPosition(float, float);
	Vector* GetRandomFlyingStartPositionIOS(float, float);

	int CollidesWith(int, int);
	int GetRandomValue();
};

#endif
