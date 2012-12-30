#ifndef TARGET_H
#define TARGET_H

#include "vector.h"

class Target{
private:
	Vector* position;
	
public:
	Target(int, int);
	int getPositionX();
	int getPositionY();
	int colidesWith(int, int);
	void update();
};

#endif
