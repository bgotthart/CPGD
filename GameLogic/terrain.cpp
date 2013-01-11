#include "terrain.h"
#include <math.h>
#include <time.h>
#include <stdlib.h>
#include <stdio.h>

Terrain::Terrain(int width, int height, int x, int y)
{
	this->width = width;
	this->height = height;
	this->playerX = x;
	this->playerY = y;
	srand (time(NULL));

}
void Terrain::GenerateTerrain()
{
		
}
Vector* Terrain::GetRandomTargetPosition(float targetWidth, float targetHeight)
{      
        /*     
    int minY = targetHeight / 2;
	int maxY = this->height - this->playerY;
	int rangeY = maxY - minY;
	int actualY = (rand() % rangeY) + minY;
	
	int minX = targetWidth / 2;
	int maxX = this->width - targetWidth/2;
	int rangeX = maxX - minX;
	int actualX = (rand() % rangeX) + minX; 
        */
    
    int actualX = 0;
    int actualY = 0;
	
	actualX = calcRandomX(targetWidth);
    actualY = calcRandomY(targetHeight);
		
	return new Vector(actualX, actualY);
}
Vector* Terrain::GetRandomFlyingStartPosition(float targetWidth, float targetHeight)
{
	
	int minY = targetHeight / 2;
	int maxY = this->playerY - targetHeight*1.5; 
	int actualY = (rand() % maxY) +  minY;
	
	int maxX = targetWidth * 2;
	int actualX = -(rand() % maxX);

	return new Vector(actualX, actualY);

}
Vector* Terrain::GetRandomFlyingStartPositionIOS(float targetWidth, float targetHeight)
{
	int minY = this->playerY;
	int maxY = this->height - targetHeight*2;
	int actualY = (rand() % maxY) +  minY;
	
	return new Vector(-targetWidth, actualY);
}

int Terrain::calcRandomX(float targetWidth){
    int minX = targetWidth / 2;
	int maxX = this->playerX - targetWidth * 2;
	return(rand() % maxX) +  minX;
}
	
    
int Terrain::calcRandomY(float targetHeight){
    int minY = targetHeight / 2;
	int maxY = this->playerY - targetHeight;
	return (rand() % maxY) +  minY;
}


int Terrain::GetRandomValue(){
	
	return (rand() % 2);
}
