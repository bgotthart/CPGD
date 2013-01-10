#include "terrain.h"
#include <math.h>
#include <time.h>
#include <stdlib.h>
#include <stdio.h>

Terrain::Terrain(int width, int height)
{
	this->width = width;
	this->height = height;
	srand (time(NULL));

}
void Terrain::GenerateTerrain()
{
		
}
Vector* Terrain::GetRandomTargetPosition(float targetWidth, float targetHeight)
{
	int minY = targetHeight / 2;
	int maxY = this->height - targetHeight/2;
	int rangeY = maxY - minY;
	int actualY = (rand() % rangeY) + minY;
	
	int minX = targetWidth / 2;
	int maxX = this->width - targetWidth/2;
	int rangeX = maxX - minX;
	int actualX = (rand() % rangeX) + minX;
	
	return new Vector(actualX, actualY);
}
Vector* Terrain::GetRandomFlyingStartPosition(float targetWidth, float targetHeight)
{
	//TODO adapt flying path
	int minY = targetHeight / 2;
	int maxY = this->height - targetHeight/2;
	int rangeY = maxY - minY;
	int actualY = (rand() % rangeY) + minY;

	return new Vector(0, actualY);

}
int Terrain::CollidesWith(int x, int y)
{
	return 0;
}
int Terrain::GetRandomValue(){
	
	return (rand() % 2);
}