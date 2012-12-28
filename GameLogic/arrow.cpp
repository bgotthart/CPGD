#include "arrow.h"

Arrow::Arrow(int x, int y){
	this->x = x;
	this->y = y;
	this->velX = 0;
	this->velY = 0;
	//this->gravity = 1.0f;
}

int Arrow::getPositionX(){
	return x;
}

int Arrow::getPositionY(){
	return y;
}

void Arrow::shootArrow(int mouseX, int mouseY, float strength){
	this->velX = (mouseX - this->x)/100 * strength;
	this->velY = (mouseY - this->y)/100 * strength;
}

void Arrow::update(){
	this->x += this->velX;
	this->y += this->velY;
	
	//this->velY += this->gravity;
}
