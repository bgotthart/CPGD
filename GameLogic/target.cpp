#include "target.h"

Target::Target(int x, int y){
	this->x = x;
	this->y = y;
}

int Target::getPositionX(){
	return x;
}

int Target::getPositionY(){
	return y;
}

int Target::colidesWith(int arrowX, arrowY){}
void Target::update(){}
