#ifndef TARGET_H
#define TARGET_H

#include "vector.h"

class Target{
private:
	int width;
	int height;
	
protected:
	Vector* position;
	
public:
	Target(int, int, int, int);
	int getPositionX();
	int getPositionY();
	int colidesWith(int, int);
	void update();
};

#endif
