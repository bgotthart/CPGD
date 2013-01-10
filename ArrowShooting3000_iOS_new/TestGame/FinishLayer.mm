//
//  GameOverLayer.m
//  Cocos2DSimpleGame
//
//  Created by Ray Wenderlich on 11/13/12.
//  Copyright 2012 Razeware LLC. All rights reserved.
//

#import "FinishLayer.h"
#import "ArrowShooting3000.h"
//#import "LevelManager.h"
#import "score.h"

@implementation FinishLayer

+(CCScene *) sceneWithFinish {
    CCScene *scene = [CCScene node];
	FinishLayer *layer = [[[FinishLayer alloc] initFinishScreen] autorelease];
    [scene addChild: layer];
    return scene;
}

- (id)initFinishScreen {
	CGSize winSize = [CCDirector sharedDirector].winSize;

	
    if ((self = [super initWithColor:ccc4(0, 0, 0, 0)])) {
			Score *score = Score::getInstance();
			NSString *message = [NSString stringWithFormat:@"Score: %i", score->getScore()];
			
			CCLabelTTF * label = [CCLabelTTF labelWithString:message fontName:@"Arial" fontSize:16];
			label.color = ccc3(255,255,255);
			label.position = ccp(winSize.width - label.contentSize.width/2, winSize.height/2);
			[self addChild:label];
		}
    return self;
}

@end
