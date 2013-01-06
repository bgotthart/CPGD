#include <string.h>
#include <jni.h>

#include "com_cpgd_arrowshooting3000_PlayerProxy.h"
#include "com_cpgd_arrowshooting3000_ArrowProxy.h"
#include "com_cpgd_arrowshooting3000_TargetProxy.h"
#include "com_cpgd_arrowshooting3000_FlyingTargetProxy.h"
#include "com_cpgd_arrowshooting3000_TerrainProxy.h"

#include "../../GameLogic/player.h"
#include "../../GameLogic/arrow.h"
#include "../../GameLogic/target.h"
#include "../../GameLogic/flyingTarget.h"
#include "../../GameLogic/terrain.h"


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

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_startArrow
  (JNIEnv * env, jobject o, jlong obj)
{
	Arrow* arrow = (Arrow*) obj;
	arrow->startArrow();
	return (long) arrow;
}

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_shootArrow
  (JNIEnv * env, jobject o, jint mouseX, jint mouseY, jlong obj)
{
	Arrow* arrow = (Arrow*) obj;
	arrow->shootArrow(mouseX, mouseY);
	return (long) arrow;
}

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_update
  (JNIEnv * env, jobject o, jlong obj)
{
	Arrow* arrow = (Arrow*) obj;
	arrow->update();
	return (long) arrow;
}


// ##################### Target ###############################
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_TargetProxy_targetProxy
  (JNIEnv * env, jobject o, jint x, jint y, jint width, jint height)
{
	Target* tmp = new Target(x, y, width, height);
	return (long) tmp;
}

JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_TargetProxy_getPositionX
  (JNIEnv * env, jobject o, jlong obj)
{
	Target* target = (Target*) obj;
	return target->getPositionX();
}

JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_TargetProxy_getPositionY
  (JNIEnv * env, jobject o, jlong obj)
{
	Target* target = (Target*) obj;
	return target->getPositionY();
}

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_TargetProxy_update
  (JNIEnv * env, jobject o, jlong obj)
{
	Target* target = (Target*) obj;
	target->update();
	return (long) target;
}

JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_TargetProxy_collidesWith
  (JNIEnv * env, jobject o, jint arrowX, jint arrowY, jlong obj)
{
	Target* target = (Target*) obj;
	return target->colidesWith(arrowX, arrowY);
}


// ##################### FlyingTarget ###############################
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_FlyingTargetProxy_flyingTargetProxy
  (JNIEnv * env, jobject o, jint x, jint y, jint width, jint height)
{
	Target* tmp = new FlyingTarget(x, y, width, height);
	return (long) tmp;
}

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_FlyingTargetProxy_update
  (JNIEnv * env, jobject o, jlong obj)
{
	FlyingTarget* target = (FlyingTarget*) obj;
	target->update();
	return (long) target;
}



// ##################### Terrain ###############################
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_TerrainProxy_terrain
  (JNIEnv *, jobject, jint width, jint height)
{
	Terrain* tmp = new Terrain(width, height);
	return (long) tmp;
}

JNIEXPORT jfloat JNICALL Java_com_cpgd_arrowshooting3000_TerrainProxy_GetRandomTargetPositionX
  (JNIEnv *, jobject, jfloat targetWidth, jfloat targetHeight, jlong obj)
{
	Terrain* terrain = (Terrain*) obj;
	return terrain->GetRandomTargetPosition(targetWidth, targetHeight)->x;
}


JNIEXPORT jfloat JNICALL Java_com_cpgd_arrowshooting3000_TerrainProxy_GetRandomTargetPositionY
  (JNIEnv *, jobject, jfloat targetWidth, jfloat targetHeight, jlong obj)
{
	Terrain* terrain = (Terrain*) obj;
	return terrain->GetRandomTargetPosition(targetWidth, targetHeight)->y;
}

JNIEXPORT jfloat JNICALL Java_com_cpgd_arrowshooting3000_TerrainProxy_GetRandomFlyingStartPositionX
  (JNIEnv *, jobject, jfloat targetWidth, jfloat targetHeight, jlong obj)
{
	Terrain* terrain = (Terrain*) obj;
	return terrain->GetRandomFlyingStartPosition(targetWidth, targetHeight)->x;
}

JNIEXPORT jfloat JNICALL Java_com_cpgd_arrowshooting3000_TerrainProxy_GetRandomFlyingStartPositionY
  (JNIEnv *, jobject, jfloat targetWidth, jfloat targetHeight, jlong obj)
{
	Terrain* terrain = (Terrain*) obj;
	return terrain->GetRandomFlyingStartPosition(targetWidth, targetHeight)->y;
}
