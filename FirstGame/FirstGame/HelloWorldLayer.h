//
//  HelloWorldLayer.h
//  FirstGame
//
//  Created by Bianca Gotthart on 12/10/12.
//  Copyright __MyCompanyName__ 2012. All rights reserved.
//

#import <GameKit/GameKit.h>

// When you import this file, you import all the cocos2d classes
#import "cocos2d.h"

// HelloWorldLayer
@interface HelloWorldLayer : CCLayerColor <GKAchievementViewControllerDelegate, GKLeaderboardViewControllerDelegate>
{
	NSMutableArray *_monsters;
	NSMutableArray *_projectiles;
	CCSprite *_player;
	CCSprite *_nextProjectile;
	int _monstersDestroyed;

}

// returns a CCScene that contains the HelloWorldLayer as the only child
+(CCScene *) scene;

@end
