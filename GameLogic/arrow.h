#ifndef ARROW_H
#define ARROW_H

#include "vector.h"
#include <ctime>

class Arrow{
	private:
		Vector* position;
		Vector* velocity;
		float gravity;
		float rotation;
		clock_t begin;
	
	public:
		Arrow(int, int);
		int getPositionX();
		int getPositionY();
		void startArrow();
		void shootArrow(int, int);
	    void update();
	    float getRotation();
};

#endif
