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

int FlyingTarget::colidesWith(int arrowX, int arrowY){
    
        if(Target::colidesWith(arrowX, arrowY) == 1){
               score->addScore(10);
               return 1;
        }
        return 0;
}
