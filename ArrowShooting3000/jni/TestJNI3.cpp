#include <string.h>
#include <jni.h>
#include "com_example_testjni3_PlayerProxy.h"

JNIEXPORT jint JNICALL Java_com_example_testjni3_PlayerProxy_getPositionX
  (JNIEnv *, jobject)
{
	return 200;
}


