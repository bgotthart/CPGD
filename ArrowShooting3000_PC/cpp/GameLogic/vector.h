#ifndef VECTOR_H
#define VECTOR_H

class Vector
{
public:
	float x;
	float y;
	
	Vector();
	Vector(float, float);
	
	void normalize();
	void multiply(float);
	float multiply(Vector*);
	void add(Vector*);
	float getLength();
};

#endif
