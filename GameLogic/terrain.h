#ifndef TERRAIN_H
#ifndef TERRAIN_H

class Terrain{
private:
	int alphamap[0];
	
public:
	Terrain(int);
	void GenerateTerrain()
    //vector GetRandomTargetPosition()
    int CollidesWith(int, int)
};

#endif