#include "player.h"

Player::Player(int x, int y)
{
	this->x = x;
	this->y = y;
}

int Player::getPositionX()
{
	return x;
}

int Player::getPositionY()
{
	return y;
}