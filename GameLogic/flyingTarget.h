#ifndef FLYINGTARGET_H
#define FLYINGTARGET_H

#include "target.h"
#include "vector.h"

class FlyingTarget : public Target {
	private:
		Vector* velocity;
	public:
		FlyingTarget(int, int, int, int);
		void update();
		int colidesWith(int, int);
	
};

#endif

