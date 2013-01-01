#include "target.h"

Target::Target(int x, int y, int width, int height){
	this->position = new Vector(x, y);
	this->width = width;
	this->height = height;
}

int Target::getPositionX(){
	return position->x;
}

int Target::getPositionY(){
	return position->y;
}

int Target::colidesWith(int arrowX, int arrowY){
	if(this->position->x < arrowX && arrowX < this->position->x + this->width &&
		 this->position->y < arrowY && arrowY < this->position->y + this->height) {
		return 1;
	}
	return 0;}

void Target::update(){

}
