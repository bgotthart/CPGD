#ifndef ARROW_H
#define ARROW_H

#include "vector.h"

class Arrow{
	private:
		Vector* position;
		Vector* velocity;
		float gravity;
		float rotation;
	
	public:
		Arrow(int, int);
		int getPositionX();
		int getPositionY();
		void shootArrow(int, int, float);
	    void update();
	    float getRotation();
};

#endif
