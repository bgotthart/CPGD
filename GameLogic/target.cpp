#include "target.h"

Target::Target(int x, int y){
	this->position = new Vector(x, y);
}

int Target::getPositionX(){
	return position->x;
}

int Target::getPositionY(){
	return position->y;
}

int Target::colidesWith(int arrowX, int arrowY){

}

void Target::update(){

}
