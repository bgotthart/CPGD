/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_cpgd_arrowshooting3000_ArrowProxy */

#ifndef _Included_com_cpgd_arrowshooting3000_ArrowProxy
#define _Included_com_cpgd_arrowshooting3000_ArrowProxy
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_cpgd_arrowshooting3000_ArrowProxy
 * Method:    arrowProxy
 * Signature: (II)J
 */
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_arrowProxy
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_cpgd_arrowshooting3000_ArrowProxy
 * Method:    getPositionX
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_getPositionX
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_cpgd_arrowshooting3000_ArrowProxy
 * Method:    getPositionY
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_getPositionY
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_cpgd_arrowshooting3000_ArrowProxy
 * Method:    getRotation
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_getRotation
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_cpgd_arrowshooting3000_ArrowProxy
 * Method:    startArrow
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_startArrow
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_cpgd_arrowshooting3000_ArrowProxy
 * Method:    shootArrow
 * Signature: (IIFJ)J
 */
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_shootArrow
  (JNIEnv *, jobject, jint, jint, jfloat, jlong);

/*
 * Class:     com_cpgd_arrowshooting3000_ArrowProxy
 * Method:    update
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_ArrowProxy_update
  (JNIEnv *, jobject, jlong);

#ifdef __cplusplus
}
#endif
#endif
