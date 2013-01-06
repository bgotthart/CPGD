#include "arrowHud.h"

ArrowHud::ArrowHud(float hudSpritewidth){
	this->hudSpriteWidth = hudSpriteWidth;
	this->maxStrength = 1000.0f;
}

float ArrowHud::getCurrentWidth(float strength) {
	float scale = strength / maxStrength;
	return hudSpriteWidth * scale;
}
