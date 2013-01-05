#ifndef TERRAIN_H
#define TERRAIN_H

#include "vector.h"

class Terrain{
public:
	int width;
	int height;

private:
	int alphamap[0];
	
public:
	Terrain(int, int);
	void GenerateTerrain();
	Vector* GetRandomTargetPosition(float, float);
	Vector* GetRandomFlyingStartPosition(float, float);
	int CollidesWith(int, int);
};

#endif
