#ifndef TERRAIN_H
#define TERRAIN_H

#include "vector.h"

class Terrain{
public:
	int width;
	int height;
	
public:
	Terrain(int, int);
	Vector* GetRandomTargetPosition(float, float);
	Vector* GetRandomFlyingStartPosition(float, float);
	int GetRandomValue();
};

#endif
