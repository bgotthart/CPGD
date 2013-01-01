#include "arrow.h"
#include <math.h>

Arrow::Arrow(int x, int y, float g){
	this->position = new Vector(x, y);
	this->velocity = new Vector();
	this->gravity = g;
}

int Arrow::getPositionX(){
	return this->position->x;
}

int Arrow::getPositionY(){
	return this->position->y;
}

float Arrow::getRotation() {
	return this->rotation;
}
void Arrow::setPositionY(int y){
	this->position->y = y;
}
void Arrow::shootArrow(int mouseX, int mouseY, float strength){
	this->velocity->x = mouseX - this->position->x;
	this->velocity->y = mouseY - (320-this->position->y);
	this->velocity->normalize();
	this->velocity->multiply(strength);
}

void Arrow::update(){
	this->position->add(this->velocity);
	this->velocity->y += this->gravity;

	float cosAlpha = (-this->velocity->x / this->velocity->getLength());
	this->rotation = acos(cosAlpha);
	if (this->velocity->y > 0) this->rotation = 2* 3.14 - this->rotation;
}
