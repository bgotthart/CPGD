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

+(CCScene *) sceneWithFinish:(ccTime)time {
	
	NSLog(@"%s", __PRETTY_FUNCTION__);

    CCScene *scene = [CCScene node];
	ArrowShooting3000 *layer = [FinishLayer node];
	
	[scene addChild: layer];
	
	
    return scene;
}


-(id) init{
	CGSize winSize = [CCDirector sharedDirector].winSize;
NSLog(@"%s", __PRETTY_FUNCTION__);
	
	self.isTouchEnabled = YES;



	if( (self=[super init])) {
			Score *score = Score::getInstance();
			NSString *message = @"The Game is over.";
			
			CCLabelTTF * label1 = [CCLabelTTF labelWithString:message fontName:@"Arial" fontSize:18];
			label1.color = ccc3(255,255,255);
			label1.position = ccp(winSize.width/2, winSize.height/2+50);
			[self addChild:label1];
		
			message = [NSString stringWithFormat:@"Score: %i", score->getScore()];
		
			CCLabelTTF * label2 = [CCLabelTTF labelWithString:message fontName:@"Arial" fontSize:22];
			label2.color = ccc3(255,255,255);
		label2.position = ccp(winSize.width/2, label1.position.y - label1.contentSize.height-10);
			[self addChild:label2];
		
		message = @"Touch the screen to start the game again";
		
		CCLabelTTF * label3 = [CCLabelTTF labelWithString:message fontName:@"Arial" fontSize:18];
		label3.color = ccc3(255,255,255);
		label3.position = ccp(winSize.width/2, label2.position.y - label2.contentSize.height-10);
		[self addChild:label3];

		
		
		score->resetScore();
		//[self scheduleOnce:@selector(makeTransition:) delay:1];

		}
    return self;
}

// In one second transition to the new scene


-(void) makeTransition:(ccTime)dt
{
	[[CCDirector sharedDirector] replaceScene:[CCTransitionFade transitionWithDuration:1.0 scene:[ArrowShooting3000 scene] withColor:ccWHITE]];
}


-(void) onEnter {
	NSLog(@"%s", __PRETTY_FUNCTION__);

	[super onEnter];
	self.isTouchEnabled = YES;
}
-(void) registerWithTouchDispatcher
{
	NSLog(@"%s", __PRETTY_FUNCTION__);

	[[CCTouchDispatcher sharedDispatcher] addTargetedDelegate:self priority:1
																						swallowsTouches:YES];
	
	
}

- (BOOL)ccTouchBegan:(UITouch *)touch withEvent:(UIEvent *)event {
	
	[self makeTransition:0.1];
	return YES;
}


@end
