#include "flyingTarget.h"

FlyingTarget::FlyingTarget(int x, int y, int width, int height) : Target(x, y, width, height)
{
	this->velocity = new Vector();
}


void FlyingTarget::update()
{
	Target::update();
	this->position->x += 1;
}
