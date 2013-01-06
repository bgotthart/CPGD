#ifndef ARROWHUD_H
#define ARROWHUD_H

class ArrowHud {
	private:
		float hudSpriteWidth;
		float maxStrength;
	
	public:
		ArrowHud(float);
		float getCurrentWidth(float);
		float getMaxStrength();
};

#endif
