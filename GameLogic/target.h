#ifndef TARGET_H
#ifndef TARGET_H

class Target{
private:
	int x;
	int y;
	
public:
	Target(int, int);
	int getPositionX();
	int getPositionY();
	int colidesWith(int, int);
	void update();
};

#endif
