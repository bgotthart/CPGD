#ifndef ARROW_H
#define ARROW_H

class Arrow{
	private:
		int x;
		int y;
		int velX;
		int velY;
		//float gravity;
	
	public:
		Arrow(int, int);
		int getPositionX();
		int getPositionY();
		void shootArrow(int, int, float);
	    void update();
};

#endif
