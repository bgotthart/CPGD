#include "arrow.h"

Arrow::Arrow(int x, int y){
	this->x = x;
	this->y = y;
	this->velX = 0;
	this->velY = 0;
}

int Arrow::getPositionX(){
	return x;
}

int Arrow::getPositionY(){
	return y;
}

void Arrow::shootArrow(int mouseX, int mouseY, float strength){
	
}

void Arrow:update(){

}