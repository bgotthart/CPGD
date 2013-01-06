#include "arrow.h"
#include <math.h>
#include <sys/time.h>

Arrow::Arrow(int x, int y){
	this->position = new Vector(x, y);
	this->velocity = new Vector();
	this->gravity = 0.1;
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

void Arrow::startArrow() {
	begin = clock();
}

void Arrow::shootArrow(int mouseX, int mouseY){

	clock_t end = clock();
	double elapsed = double(end - begin) / CLOCKS_PER_SEC;
	float strength = (float) elapsed * 30;

	this->velocity->x = mouseX - this->position->x;
	this->velocity->y = mouseY - this->position->y;

	this->velocity->normalize();
	this->velocity->multiply(strength);
}

void Arrow::update(){
	this->position->add(velocity);
	this->velocity->y += this->gravity;

	float cosAlpha = (-this->velocity->x / this->velocity->getLength());
	this->rotation = acos(cosAlpha);
	if (this->velocity->y > 0) this->rotation = 2* 3.14 - this->rotation;
}

void Arrow::setPositionX(float x){
	this->position->x = x;
}
void Arrow::setPositionY(float y){
	this->position->y = y;
}
