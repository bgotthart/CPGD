#include "terrain.h"
#include <math.h>
#include <time.h>
#include <stdlib.h>

Terrain::Terrain(int x, int width, int height)
{
	this->width = width;
	this->height = height;
}
void Terrain::GenerateTerrain()
{
		
}
Vector* Terrain::GetRandomTargetPosition(float targetWidth, float targetHeight)
{
	srand(time(0));
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
	srand(time(0));
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
