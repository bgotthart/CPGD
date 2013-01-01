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
		Arrow(int x, int y, float g = 0.1f);
		int getPositionX();
		int getPositionY();
		void setPositionY(int);

		void shootArrow(int, int, float);
	    void update();
	    float getRotation();
};

#endif
