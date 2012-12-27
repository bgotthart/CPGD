#include <string.h>
#include <jni.h>
#include "../../GameLogic/player.h"
#include "com_cpgd_arrowshooting3000_PlayerProxy.h"
#include "com_cpgd_arrowshooting3000_ArrowProxy.h"

// ##################### Player ###############################
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_PlayerProxy_playerProxy
  (JNIEnv * env, jobject o, jint x, jint y)
{
	Player* tmp = new Player(x, y);
	return (long) tmp;
}

JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_PlayerProxy_getPositionX
  (JNIEnv * env, jobject o, jlong d)
{
	Player* p = (Player*) d;
	return p->getPositionX();
}

JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_PlayerProxy_getPositionY
  (JNIEnv * env, jobject o, jlong d)
{
	Player* p = (Player*) d;
	return p->getPositionY();
}

// ######### TMP -> AUSLAGERN!!! #########
class Arrow {
private:
	int positionX;
	int positionY;
	float velocityX;
	float velocityY;

public:
	Arrow(int x, int y) {positionX = x;	positionY = y; velocityX = 0; velocityY = 0;}
	int getPositionX() { return positionX; }
	int getPositionY() { return positionY; }
	void shootArrow(int mouseX, int mouseY, float strength) {
		velocityX = (mouseX - positionX)/100 * strength;
		velocityY = (mouseY - positionY)/100 * strength;
	}
	void update() {
		positionX += velocityX;
		positionY += velocityY;
	}
};


// ##################### Arrow ###############################
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_arrowProxy
  (JNIEnv * env, jobject o, jint x, jint y)
{
	Arrow* tmp = new Arrow(x, y);
	return (long) tmp;
}

JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_getPositionX
  (JNIEnv * env, jobject o, jlong obj)
{
	Arrow* arrow = (Arrow*) obj;
	return arrow->getPositionX();
}

JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_getPositionY
  (JNIEnv * env, jobject o, jlong obj)
{
	Arrow* arrow = (Arrow*) obj;
	return arrow->getPositionY();
}

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_shootArrow
  (JNIEnv * env, jobject o, jint mouseX, jint mouseY, jfloat strength, jlong obj)
{
	Arrow* arrow = (Arrow*) obj;
	arrow->shootArrow(mouseX, mouseY, strength);
	return (long) arrow;
}

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_update
  (JNIEnv * env, jobject o, jlong obj)
{
	Arrow* arrow = (Arrow*) obj;
	arrow->update();
	return (long) arrow;
}
