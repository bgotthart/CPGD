//
//  ArrowShooting3000.h
//  ArrowShooting3000_iOS
//
//  Created by Bianca Gotthart on 1/7/13.
//  Copyright __MyCompanyName__ 2013. All rights reserved.
//


#import <GameKit/GameKit.h>

// When you import this file, you import all the cocos2d classes
#import "cocos2d.h"
#import "TargetObjC.h"
#import "terrain.h"
#import "flyingTarget.h"
#import "arrowHud.h"
#import "player.h"
#define PTM_RATIO 32

//Pixel to metres ratio. Box2D uses metres as the unit for measurement.
//This ratio defines how many pixels correspond to 1 Box2D "metre"
//Box2D is optimized for objects of 1x1 metre therefore it makes sense
//to define the ratio so that your most common object type is 1x1 metre.
#define PTM_RATIO 32

// HelloWorldLayer
@interface ArrowShooting3000 : CCLayer
{
	CCTexture2D *spriteTexture_;	// weak ref
	
	BOOL _moving;
	CCSprite *_bow;
	CCAction *_bowAction;


}

// returns a CCScene that contains the HelloWorldLayer as the only child
+(CCScene *) scene;

@property() Player *player;
@property() Terrain *terrain;

@property(assign) CCSprite *greenSprite;
@property(assign) CCSprite *redSprite;
@property() ArrowHud *arrowHud;
@property (nonatomic, retain) CCSprite *bow;
@property (nonatomic, retain) CCAction *bowAction;




@end
