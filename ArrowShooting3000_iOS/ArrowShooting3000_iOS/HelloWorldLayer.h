//
//  HelloWorldLayer.h
//  ArrowShooting3000_iOS
//
//  Created by Bianca Gotthart on 12/28/12.
//  Copyright __MyCompanyName__ 2012. All rights reserved.
//


#import <GameKit/GameKit.h>

// When you import this file, you import all the cocos2d classes
#import "cocos2d.h"
//#import "Box2D.h"
#import "GLES-Render.h"
#import "TargetObjC.h"
#import "terrain.h"
#import "flyingTarget.h"
#import "arrowHud.h"
#import "player.h"


//Pixel to metres ratio. Box2D uses metres as the unit for measurement.
//This ratio defines how many pixels correspond to 1 Box2D "metre"
//Box2D is optimized for objects of 1x1 metre therefore it makes sense
//to define the ratio so that your most common object type is 1x1 metre.
#define PTM_RATIO 32

// HelloWorldLayer
@interface HelloWorldLayer : CCLayer <GKAchievementViewControllerDelegate, GKLeaderboardViewControllerDelegate>
{
	CCTexture2D *spriteTexture_;	// weak ref
	b2World* world;					// strong ref
	GLESDebugDraw *m_debugDraw;		// strong ref
	
	CCSprite *_bow;
	BOOL _moving;
	CCSprite *_bear;
	CCAction *_walkAction;

}

// returns a CCScene that contains the HelloWorldLayer as the only child
+(CCScene *) scene;

@property() Player *player;
@property() Terrain *terrain;

@property(assign) CCSprite *greenSprite;
@property(assign) CCSprite *redSprite;
@property() ArrowHud *arrowHud;
@property (nonatomic, retain) CCSprite *bow;
@property (nonatomic, retain) CCSprite *bear;
@property (nonatomic, retain) CCAction *walkAction;


@end
