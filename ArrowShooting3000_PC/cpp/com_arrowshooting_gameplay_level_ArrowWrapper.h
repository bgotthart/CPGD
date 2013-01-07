/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_arrowshooting_gameplay_level_ArrowWrapper */

#ifndef _Included_com_arrowshooting_gameplay_level_ArrowWrapper
#define _Included_com_arrowshooting_gameplay_level_ArrowWrapper
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_arrowshooting_gameplay_level_ArrowWrapper
 * Method:    arrowWrapper
 * Signature: (II)J
 */
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_arrowWrapper
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_arrowshooting_gameplay_level_ArrowWrapper
 * Method:    getPositionX
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_getPositionX
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_arrowshooting_gameplay_level_ArrowWrapper
 * Method:    getPositionY
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_getPositionY
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_arrowshooting_gameplay_level_ArrowWrapper
 * Method:    getStrength
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_getStrength
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_arrowshooting_gameplay_level_ArrowWrapper
 * Method:    setPositionX
 * Signature: (FJ)J
 */
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_setPositionX
  (JNIEnv *, jobject, jfloat, jlong);

/*
 * Class:     com_arrowshooting_gameplay_level_ArrowWrapper
 * Method:    setPositionY
 * Signature: (FJ)J
 */
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_setPositionY
  (JNIEnv *, jobject, jfloat, jlong);

/*
 * Class:     com_arrowshooting_gameplay_level_ArrowWrapper
 * Method:    startArrow
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_startArrow
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_arrowshooting_gameplay_level_ArrowWrapper
 * Method:    shootArrow
 * Signature: (IIJ)J
 */
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_shootArrow
  (JNIEnv *, jobject, jint, jint, jlong);

/*
 * Class:     com_arrowshooting_gameplay_level_ArrowWrapper
 * Method:    update
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_update
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_arrowshooting_gameplay_level_ArrowWrapper
 * Method:    getRotation
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_getRotation
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_arrowshooting_gameplay_level_ArrowWrapper
 * Method:    getTouchRotation
 * Signature: (FFJ)F
 */
JNIEXPORT jfloat JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_getTouchRotation
  (JNIEnv *, jobject, jfloat, jfloat, jlong);

#ifdef __cplusplus
}
#endif
#endif
