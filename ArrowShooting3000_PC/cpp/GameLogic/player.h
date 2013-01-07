#ifndef PLAYER_H
#define PLAYER_H

class Player
{
private:
	int x;
	int y;

public:
	Player(int, int);
	int getPositionX();
	int getPositionY();
};

#endif