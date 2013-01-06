#include "arrowHud.h"

ArrowHud::ArrowHud(float hudSpriteWidth){
	this->hudSpriteWidth = hudSpriteWidth;
	this->maxStrength = 50;
}

float ArrowHud::getCurrentWidth(float strength) {
	float scale = strength / maxStrength;
	return hudSpriteWidth * scale;
}

float ArrowHud::getCurrentScale(float strength) {
	return strength / maxStrength;
}
