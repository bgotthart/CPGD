#include "arrowHud.h"
#include "config.h"

ArrowHud::ArrowHud(float hudSpriteWidth){
	this->hudSpriteWidth = hudSpriteWidth;
	this->maxStrength = MAX_STRENGTH;
}

float ArrowHud::getCurrentWidth(float strength) {
	float scale = strength / maxStrength;
	scale = (scale > 1) ? 1 : scale;
	return hudSpriteWidth * scale;
}

float ArrowHud::getCurrentScale(float strength) {
	return (strength / maxStrength > 1) ? 1 : (strength / maxStrength);
}
