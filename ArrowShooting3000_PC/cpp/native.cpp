#include <string.h>
#include <jni.h>

#include "JNIHeaders/com_cpgd_arrowshooting3000_components_ArrowHudProxy.h"
#include "JNIHeaders/com_cpgd_arrowshooting3000_components_ArrowProxy.h"
#include "JNIHeaders/com_cpgd_arrowshooting3000_components_FlyingTargetProxy.h"
#include "JNIHeaders/com_cpgd_arrowshooting3000_components_PlayerProxy.h"
#include "JNIHeaders/com_cpgd_arrowshooting3000_components_TargetProxy.h"
#include "JNIHeaders/com_cpgd_arrowshooting3000_components_TerrainProxy.h"
#include "JNIHeaders/com_cpgd_arrowshooting3000_components_ScoreProxy.h"

#include "../../GameLogic/arrowHud.h"
#include "../../GameLogic/arrow.h"
#include "../../GameLogic/flyingTarget.h"
#include "../../GameLogic/player.h"
#include "../../GameLogic/target.h"
#include "../../GameLogic/terrain.h"
#include "../../GameLogic/score.h"

// PLAYER: ***********************************************************

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_PlayerProxy_playerProxy
  (JNIEnv * env, jobject obj, jint x, jint y){
          Player* tmp = new Player(x, y);
          return (long) tmp;        
  }
  
JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_components_PlayerProxy_getPositionX
  (JNIEnv * env, jobject obj, jlong ref){
          Player* player = (Player*)ref;
          return player->getPositionX();
  }
  
JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_components_PlayerProxy_getPositionY
  (JNIEnv * env, jobject obj, jlong ref){
          Player* player = (Player*)ref;
          return player->getPositionY();
  }

// ARROW: ***********************************************************

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_ArrowProxy_arrowProxy
  (JNIEnv * env, jobject obj, jint x, jint y){
          Arrow* tmp = new Arrow(x, y);
          return (long) tmp;
  }
  
JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_components_ArrowProxy_getPositionX
  (JNIEnv * env, jobject obj, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          return arrow->getPositionX();     
  }
  
JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_components_ArrowProxy_getPositionY
  (JNIEnv * env, jobject obj, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          return arrow->getPositionY();
  }
  
JNIEXPORT jfloat JNICALL Java_com_cpgd_arrowshooting3000_components_ArrowProxy_getStrength
  (JNIEnv * env, jobject obj, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          return arrow->getStrength();        
  }
  
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_ArrowProxy_setPositionX
  (JNIEnv * env, jobject obj, jfloat x, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          arrow->setPositionX(x);
          return (long)arrow;       
  }
  
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_ArrowProxy_setPositionY
  (JNIEnv * env, jobject obj, jfloat y, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          arrow->setPositionY(y);
          return (long)arrow;        
  }
  
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_ArrowProxy_startArrow
  (JNIEnv * env, jobject obj, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          arrow->startArrow();
          return (long) arrow;        
  }
  
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_ArrowProxy_shootArrow
  (JNIEnv * env, jobject obj, jint mouseX, jint mouseY, jfloat bowCenterX, jfloat bowCenterY, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          arrow->shootArrow(mouseX, mouseY, bowCenterX, bowCenterY);
          return (long) arrow;        
  }
  
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_ArrowProxy_update
  (JNIEnv * env, jobject obj, jfloat elapsed,  jlong ref){
          Arrow* arrow = (Arrow*) ref;
          arrow->update(elapsed);
          return (long) arrow;        
  }
  
JNIEXPORT jfloat JNICALL Java_com_cpgd_arrowshooting3000_components_ArrowProxy_getRotation
  (JNIEnv * env, jobject obj, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          return arrow->getRotation();         
  }
  
JNIEXPORT jfloat JNICALL Java_com_cpgd_arrowshooting3000_components_ArrowProxy_getTouchRotation
  (JNIEnv * env, jobject obj, jfloat mouseX, jfloat mouseY, jfloat bowCenterX, jfloat bowCenterY, jlong ref){
          Arrow* arrow = (Arrow*) ref;
          return arrow->getTouchRotation(mouseX, mouseY, bowCenterX, bowCenterY); 
  }
  
// ARROWHUD: ***********************************************************

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_ArrowHudProxy_arrowHudProxy
  (JNIEnv * env, jobject obj, jfloat width){
          ArrowHud* tmp = new ArrowHud(width);
          return (long) tmp;        
  }

JNIEXPORT jfloat JNICALL Java_com_cpgd_arrowshooting3000_components_ArrowHudProxy_getCurrentWidth
  (JNIEnv * env, jobject obj, jfloat strength, jlong ref){
          ArrowHud* arrowHud = (ArrowHud*)ref;
          return arrowHud->getCurrentWidth(strength);       
  }
  
JNIEXPORT jfloat JNICALL Java_com_cpgd_arrowshooting3000_components_ArrowHudProxy_getCurrentScale
  (JNIEnv * env, jobject obj, jfloat strength, jlong ref){
          ArrowHud* arrowHud = (ArrowHud*)ref;
          return arrowHud->getCurrentScale(strength);         
  }
    
// TARGET: ***********************************************************

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_TargetProxy_targetProxy
  (JNIEnv * env, jobject obj, jint x, jint y, jint w, jint h){
          Target* tmp = new Target(x, y, w, h);
          return (long)tmp;        
  }
  
JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_components_TargetProxy_getPositionX
  (JNIEnv * env, jobject obj, jlong ref){
          Target* target = (Target*)ref;
          return target->getPositionX();
  }
  
JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_components_TargetProxy_getPositionY
  (JNIEnv * env, jobject obj, jlong ref){
          Target* target = (Target*)ref;
          return target->getPositionY();        
  }
  
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_TargetProxy_setPositionX
  (JNIEnv * env, jobject obj, jfloat x, jlong ref){
          Target* target = (Target*)ref;
          target->setPositionX(x);
          return (long)ref;
  }
  
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_TargetProxy_setPositionY
  (JNIEnv * env, jobject obj, jfloat y, jlong ref){
          Target* target = (Target*)ref;
          target->setPositionY(y);
          return (long)ref;
  }
  
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_TargetProxy_update
  (JNIEnv * env, jobject obj, jlong ref){
          Target* target = (Target*)ref;
          target->update();
          return (long)target;        
  }
  
JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_components_TargetProxy_collidesWith
  (JNIEnv * env, jobject obj, jint mouseX, jint mouseY, jlong ref){
          Target* target = (Target*)ref;
          return target->colidesWith(mouseX, mouseY);        
  }

// FLYING TARGET: ***********************************************************

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_FlyingTargetProxy_flyingTargetProxy
  (JNIEnv * env, jobject obj, jint x, jint y, jint w, jint h){
          Target* tmp = new FlyingTarget(x, y, w, h);
          return (long)tmp;              
  }
  
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_FlyingTargetProxy_update
  (JNIEnv * env, jobject obj, jlong ref){
          FlyingTarget* target = (FlyingTarget*)ref;
          target->update();
          return (long)target;         
  }
  
JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_components_FlyingTargetProxy_collidesWith
  (JNIEnv * env, jobject obj, jint mouseX, jint mouseY, jlong ref){
          FlyingTarget* target = (FlyingTarget*)ref;
          return target->colidesWith(mouseX, mouseY);        
  }
  
// TERRAIN: ***********************************************************

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_TerrainProxy_terrainProxy
  (JNIEnv * env, jobject obj, jint w, jint h, jint x, jint y){
          Terrain* tmp = new Terrain(w, h, x, y);
          return (long)tmp;
  }
  
JNIEXPORT jfloat JNICALL Java_com_cpgd_arrowshooting3000_components_TerrainProxy_getRandomTargetPositionX
  (JNIEnv * env, jobject obj, jfloat targetW, jfloat targetH, jlong ref){
          Terrain* terrain = (Terrain*)ref;
          return terrain->GetRandomTargetPosition(targetW, targetH)->x;      
  }
  
JNIEXPORT jfloat JNICALL Java_com_cpgd_arrowshooting3000_components_TerrainProxy_getRandomTargetPositionY
  (JNIEnv * env, jobject obj, jfloat targetW, jfloat targetH, jlong ref){
          Terrain* terrain = (Terrain*)ref; 
          return terrain->GetRandomTargetPosition(targetW, targetH)->y;
  }
  
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_TerrainProxy_getRandomFlyingTargetPositionX
  (JNIEnv * env, jobject obj, jfloat targetW, jfloat targetH, jlong ref){
          Terrain* terrain = (Terrain*)ref; 
          return terrain->GetRandomFlyingStartPosition(targetW, targetH)->x;
  }
  
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_TerrainProxy_getRandomFlyingTargetPositionY
  (JNIEnv * env, jobject obj, jfloat targetW, jfloat targetH, jlong ref){
          Terrain* terrain = (Terrain*)ref;
          return terrain->GetRandomFlyingStartPosition(targetW, targetH)->y;       
  }

// SCORE: ***********************************************************

JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_ScoreProxy_scoreProxy
  (JNIEnv * env, jobject obj){
          Score* tmp = Score::getInstance();
          return (long)tmp;        
  }
  
JNIEXPORT jlong JNICALL Java_com_cpgd_arrowshooting3000_components_ScoreProxy_resetScore
  (JNIEnv * env, jobject obj, jlong ref){
          Score* score = (Score*)ref;
          score->resetScore();    
          return (long) score;   
  }
  
JNIEXPORT jint JNICALL Java_com_cpgd_arrowshooting3000_components_ScoreProxy_getScore
  (JNIEnv * env, jobject obj, jlong ref){
          Score* score = (Score*)ref;
          return score->getScore();       
  }
