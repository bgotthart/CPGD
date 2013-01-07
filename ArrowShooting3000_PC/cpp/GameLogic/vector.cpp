#include "vector.h"
#include <math.h>

Vector::Vector()
{
	this->x = 0;
	this->y = 0;
}

Vector::Vector(float x, float y)
{
	this->x = x;
	this->y = y;
}

void Vector::normalize()
{
	double d = sqrt(this->x*this->x + this->y*this->y);
	this->x = this->x/d;
	this->y = this->y/d;
}

void Vector::multiply(float scalar)
{
	this->x *= scalar;
	this->y *= scalar;
}

float Vector::multiply(Vector* vector)
{
	return (this->x * vector->x + this->y * vector->y);
}

void Vector::add(Vector* vector)
{
	this->x += vector->x;
	this->y += vector->y;
}

float Vector::getLength()
{
	return (float)(sqrt(this->x*this->x + this->y*this->y));
}