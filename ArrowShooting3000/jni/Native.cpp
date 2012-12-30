#include <string.h>
#include <jni.h>

#include "com_cpgd_arrowshooting3000_PlayerProxy.h"
#include "com_cpgd_arrowshooting3000_ArrowProxy.h"
#include "com_cpgd_arrowshooting3000_GroundTargetProxy.h"

#include "../../GameLogic/player.h"
#include "../../GameLogic/arrow.h"
#include "../../GameLogic/groundTarget.h"


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

JNIEXPORT jfloat JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_getRotation
  (JNIEnv * env, jobject o, jlong obj)
{
	Arrow* arrow = (Arrow*) obj;
	return arrow->getRotation();
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


// ##################### Ground Target ###############################
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_GroundTargetProxy_groundTargetProxy
  (JNIEnv * env, jobject o, jint x, jint y)
{
	Target* tmp = new GroundTarget(x, y);
	return (long) tmp;
}

JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_GroundTargetProxy_getPositionX
  (JNIEnv * env, jobject o, jlong obj)
{
	Target* target = (Target*) obj;
	return target->getPositionX();
}

JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_GroundTargetProxy_getPositionY
  (JNIEnv * env, jobject o, jlong obj)
{
	Target* target = (Target*) obj;
	return target->getPositionY();
}

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_GroundTargetProxy_update
  (JNIEnv * env, jobject o, jlong obj)
{
	Target* target = (Target*) obj;
	target->update();
	return (long) target;
}
