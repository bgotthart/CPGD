#include <string.h>
#include <jni.h>

#include "com_arrowshooting_gameplay_level_ArrowHudWrapper.h"
#include "com_arrowshooting_gameplay_level_ArrowWrapper.h"
#include "com_arrowshooting_gameplay_level_FlyingTargetWrapper.h"
#include "com_arrowshooting_gameplay_level_PlayerWrapper.h"
#include "com_arrowshooting_gameplay_level_TargetWrapper.h"
#include "com_arrowshooting_gameplay_level_TerrainWrapper.h"
#include "com_arrowshooting_gameplay_level_ScoreWrapper.h"

#include "GameLogic/arrowHud.h"
#include "GameLogic/arrow.h"
#include "GameLogic/flyingTarget.h"
#include "GameLogic/player.h"
#include "GameLogic/target.h"
#include "GameLogic/terrain.h"
#include "GameLogic/score.h"

// PLAYER: ***********************************************************

JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_PlayerWrapper_playerWrapper
  (JNIEnv * env, jobject obj, jint x, jint y){
          Player* tmp = new Player(x, y);
          return (long) tmp;        
  }
  
JNIEXPORT jint JNICALL Java_com_arrowshooting_gameplay_level_PlayerWrapper_getPositionX
  (JNIEnv * env, jobject obj, jlong ref){
          Player* player = (Player*)ref;
          return player->getPositionX();
  }
  
JNIEXPORT jint JNICALL Java_com_arrowshooting_gameplay_level_PlayerWrapper_getPositionY
  (JNIEnv * env, jobject obj, jlong ref){
          Player* player = (Player*)ref;
          return player->getPositionY();
  }

// ARROW: ***********************************************************

JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_arrowWrapper
  (JNIEnv * env, jobject obj, jint x, jint y){
          Arrow* tmp = new Arrow(x, y);
          return (long) tmp;
  }
  
JNIEXPORT jint JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_getPositionX
  (JNIEnv * env, jobject obj, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          return arrow->getPositionX();     
  }
  
JNIEXPORT jint JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_getPositionY
  (JNIEnv * env, jobject obj, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          return arrow->getPositionY();
  }
  
JNIEXPORT jfloat JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_getStrength
  (JNIEnv * env, jobject obj, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          return arrow->getStrength();        
  }
  
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_setPositionX
  (JNIEnv * env, jobject obj, jfloat x, jlong ref){}
  
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_setPositionY
  (JNIEnv * env, jobject obj, jfloat y, jlong ref){}
  
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_startArrow
  (JNIEnv * env, jobject obj, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          arrow->startArrow();
          return (long) arrow;        
  }
  
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_shootArrow
  (JNIEnv * env, jobject obj, jint mouseX, jint mouseY, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          arrow->shootArrow(mouseX, mouseY);
          return (long) arrow;        
  }
  
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_update
  (JNIEnv * env, jobject obj, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          arrow->update();
          return (long) arrow;        
  }
  
JNIEXPORT jfloat JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_getRotation
  (JNIEnv * env, jobject obj, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          return arrow->getRotation();         
  }
  
JNIEXPORT jfloat JNICALL Java_com_arrowshooting_gameplay_level_ArrowWrapper_getTouchRotation
  (JNIEnv * env, jobject obj, jfloat mouseX, jfloat mouseY, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          return arrow->getTouchRotation(mouseX, mouseY); 
  }
  
// ARROWHUD: ***********************************************************

JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_ArrowHudWrapper_arrowHudWrapper
  (JNIEnv * env, jobject obj, jfloat width){
          ArrowHud* tmp = new ArrowHud(width);
          return (long) tmp;        
  }

JNIEXPORT jfloat JNICALL Java_com_arrowshooting_gameplay_level_ArrowHudWrapper_getCurrentWidth
  (JNIEnv * env, jobject obj, jfloat strength, jlong ref){
          ArrowHud* arrowHud = (ArrowHud*)ref;
          return arrowHud->getCurrentWidth(strength);       
  }
  
JNIEXPORT jfloat JNICALL Java_com_arrowshooting_gameplay_level_ArrowHudWrapper_getCurrentScale
  (JNIEnv * env, jobject obj, jfloat strength, jlong ref){
          ArrowHud* arrowHud = (ArrowHud*)ref;
          return arrowHud->getCurrentScale(strength);         
  }
    
// TARGET: ***********************************************************

JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_TargetWrapper_targetWrapper
  (JNIEnv * env, jobject obj, jint x, jint y, jint w, jint h){
          Target* tmp = new Target(x, y, w, h);
          return (long)tmp;        
  }
  
JNIEXPORT jint JNICALL Java_com_arrowshooting_gameplay_level_TargetWrapper_getPositionX
  (JNIEnv * env, jobject obj, jlong ref){
          Target* target = (Target*)ref;
          return target->getPositionX();
  }
  
JNIEXPORT jint JNICALL Java_com_arrowshooting_gameplay_level_TargetWrapper_getPositionY
  (JNIEnv * env, jobject obj, jlong ref){}
  
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_TargetWrapper_setPositionX
  (JNIEnv * env, jobject obj, jfloat x, jlong ref){
          Target* target = (Target*)ref;
          return target->getPositionY();
  }
  
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_TargetWrapper_setPositionY
  (JNIEnv * env, jobject obj, jfloat y, jlong ref){}
  
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_TargetWrapper_update
  (JNIEnv * env, jobject obj, jlong ref){
          Target* target = (Target*)ref;
          target->update();
          return (long)target;        
  }
  
JNIEXPORT jint JNICALL Java_com_arrowshooting_gameplay_level_TargetWrapper_collidesWith
  (JNIEnv * env, jobject obj, jint mouseX, jint mouseY, jlong ref){
          Target* target = (Target*)ref;
          return target->colidesWith(mouseX, mouseY);        
  }

// FLYING TARGET: ***********************************************************

JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_FlyingTargetWrapper_flyingTargetWrapper
  (JNIEnv * env, jobject obj, jint x, jint y, jint w, jint h){
          Target* tmp = new FlyingTarget(x, y, w, h);
          return (long)tmp;              
  }
  
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_FlyingTargetWrapper_update
  (JNIEnv * env, jobject obj, jlong ref){
          FlyingTarget* target = (FlyingTarget*)ref;
          target->update();
          return (long)target;         
  }
  
JNIEXPORT jint JNICALL Java_com_arrowshooting_gameplay_level_FlyingTargetWrapper_collidesWith
  (JNIEnv * env, jobject obj, jint mouseX, jint mouseY, jlong ref){
          FlyingTarget* target = (FlyingTarget*)ref;
          return target->colidesWith(mouseX, mouseY);        
  }
  
// TERRAIN: ***********************************************************

JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_TerrainWrapper_terrainWrapper
  (JNIEnv * env, jobject obj, jint w, jint h){
          Terrain* tmp = new Terrain(w, h);
          return (long)tmp;
  }
  
JNIEXPORT jfloat JNICALL Java_com_arrowshooting_gameplay_level_TerrainWrapper_getRandomTargetPositionX
  (JNIEnv * env, jobject obj, jfloat targetW, jfloat targetH, jlong ref){
          Terrain* terrain = (Terrain*)ref;
          return terrain->GetRandomTargetPosition(targetW, targetH)->x;      
  }
  
JNIEXPORT jfloat JNICALL Java_com_arrowshooting_gameplay_level_TerrainWrapper_getRandomTargetPositionY
  (JNIEnv * env, jobject obj, jfloat targetW, jfloat targetH, jlong ref){
          Terrain* terrain = (Terrain*)ref; 
          return terrain->GetRandomTargetPosition(targetW, targetH)->y;
  }
  
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_TerrainWrapper_getRandomFlyingTargetPositionX
  (JNIEnv * env, jobject obj, jfloat targetW, jfloat targetH, jlong ref){
          Terrain* terrain = (Terrain*)ref; 
          return terrain->GetRandomFlyingStartPosition(targetW, targetH)->x;
  }
  
JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_TerrainWrapper_getRandomFlyingTargetPositionY
  (JNIEnv * env, jobject obj, jfloat targetW, jfloat targetH, jlong ref){
          Terrain* terrain = (Terrain*)ref;
          return terrain->GetRandomFlyingStartPosition(targetW, targetH)->y;       
  }

// SCORE: ***********************************************************

JNIEXPORT jlong JNICALL Java_com_arrowshooting_gameplay_level_ScoreWrapper_scoreWrapper
  (JNIEnv * env, jobject obj){
          Score* tmp = Score::getInstance();
          return (long)tmp;        
  }
  
JNIEXPORT jint JNICALL Java_com_arrowshooting_gameplay_level_ScoreWrapper_getScore
  (JNIEnv * env, jobject obj, jlong ref){
          Score* score = (Score*)ref;
          return score->getScore();       
  }
