LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Native
### Add all source file names to be included in lib separated by a whitespace
LOCAL_SRC_FILES := Native.cpp
LOCAL_SRC_FILES += ../../GameLogic/player.cpp
LOCAL_SRC_FILES += ../../GameLogic/arrow.cpp
LOCAL_SRC_FILES += ../../GameLogic/vector.cpp
LOCAL_SRC_FILES += ../../GameLogic/target.cpp
LOCAL_SRC_FILES += ../../GameLogic/flyingTarget.cpp
LOCAL_SRC_FILES += ../../GameLogic/terrain.cpp
LOCAL_SRC_FILES += ../../GameLogic/arrowHud.cpp


include $(BUILD_SHARED_LIBRARY)
