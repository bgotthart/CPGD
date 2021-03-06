#include "target.h"

Target::Target(int x, int y, int width, int height){
	this->position = new Vector(x, y);
	this->width = width;
	this->height = height;
	this->score = Score::getInstance();
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
        score->addScore(10);
		return 1;
	}
	return 0;
}

void Target::update(){

}

void Target::setPositionX(float x){
	this->position->x = x;
}
void Target::setPositionY(float y){
	this->position->y = y;
}
