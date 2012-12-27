#include "target.h"
class FlyingTarget : public Target {
	private:
		int velX;
		int velY;
	public:
		FlyingTarget(int, int);
		void update();
};

