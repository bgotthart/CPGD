LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := TestJNI3
### Add all source file names to be included in lib separated by a whitespace
LOCAL_SRC_FILES := TestJNI3.cpp

include $(BUILD_SHARED_LIBRARY)
