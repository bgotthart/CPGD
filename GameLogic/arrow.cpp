#include "arrow.h"
#include "config.h"
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

int Arrow::getPositionY() {
	return this->position->y;
}

float Arrow::getStrength() {
	clock_t end = clock();
	double elapsed = double(end - begin) / CLOCKS_PER_SEC;
	return (float) elapsed * 30;
}

float Arrow::getRotation() {
	return this->rotation;
}

float Arrow::getTouchRotation(float mouseX, float mouseY, float bowCenterX, float bowCenterY) {
	Vector* direction = new Vector(mouseX - bowCenterX, mouseY - bowCenterY);
	float cosAlpha = (-direction->x / direction->getLength());
	float rot = acos(cosAlpha);
	if (direction->y > 0) rot = 2* 3.14 - rot;
	return rot;
}

void Arrow::startArrow() {
	begin = clock();
}

void Arrow::shootArrow(int mouseX, int mouseY, float bowCenterX, float bowCenterY){

	clock_t end = clock();
	double elapsed = double(end - begin) / CLOCKS_PER_SEC;
	float strength = (float) elapsed * STRENGTH_INCREASING_FACTOR;
	strength = (strength > MAX_STRENGTH) ? MAX_STRENGTH : strength;

	this->velocity->x = mouseX - bowCenterX;
	this->velocity->y = mouseY - bowCenterY;

	this->velocity->normalize();
	this->velocity->multiply(strength);
}

void Arrow::update(float elapsedMSec){
	this->position->add(velocity);
	this->velocity->y += (this->gravity * (elapsedMSec/10.0f));
	//this->velocity->x *= (elapsedMSec/17.0f);
	//this->velocity->y *= (elapsedMSec/17.0f);

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
