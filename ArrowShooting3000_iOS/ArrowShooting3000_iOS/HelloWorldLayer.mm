//
//  HelloWorldLayer.mm
//  ArrowShooting3000_iOS
//
//  Created by Bianca Gotthart on 12/28/12.
//  Copyright __MyCompanyName__ 2012. All rights reserved.
//

// Import the interfaces
#import "HelloWorldLayer.h"

// Needed to obtain the Navigation Controller
#import "AppDelegate.h"

#import "PhysicsSprite.h"

#import "ArrowsObjC.h"
#import "FlyingTargetObjC.h"

#import "TargetObjC.h"
#import "terrain.h"
#import "flyingTarget.h"

enum {
	kTagParentNode = 1,
};


#pragma mark - HelloWorldLayer

@interface HelloWorldLayer()
-(void) initPhysics;
-(void) addNewSpriteAtPosition:(CGPoint)p;
-(void) createMenu;
@end

@implementation HelloWorldLayer
Player *player;
NSMutableArray *arrowsArray;
NSMutableArray *targetsArray;
Terrain *terrain;

bool has_touched = false;
float touching_time;

float groundTargetTimer;
float groundTargetSpawnTime;
float flyingTargetTimer;
float flyingTargetSpawnTime;

+(CCScene *) scene
{
	// 'scene' is an autorelease object.
	CCScene *scene = [CCScene node];
	
	// 'layer' is an autorelease object.
	HelloWorldLayer *layer = [HelloWorldLayer node];
	
	// add layer as a child to scene
	[scene addChild: layer];
	
	// return the scene
	return scene;
}
-(id) init
{
	if( (self=[super init])) {
		
		// enable events
		
		self.isTouchEnabled = YES;
		self.isAccelerometerEnabled = YES;
		CGSize winSize = [CCDirector sharedDirector].winSize;
		terrain = new Terrain(0, winSize.width, winSize.height);
		
		
		// init physics
		//[self initPhysics];
		
		// create reset button
		//[self createMenu];
		
		//Set up sprite
		_bowSprite = [CCSprite spriteWithFile:@"bow.png"];
		player = new Player(terrain->width - _bowSprite.contentSize.width/2, terrain->height/2);
		_bowSprite.position = ccp(player->getPositionX(),player->getPositionY());
		arrowsArray = [[NSMutableArray alloc] init];
		targetsArray = [[NSMutableArray alloc] init];

		[self addChild:_bowSprite];
		
		//Timer
		groundTargetSpawnTime = 3;
		flyingTargetSpawnTime = 8;
		
		[self addGroundTarget];
		[self addFlyingTarget];
	/*
		[self addGroundTarget];
		[self addFlyingTarget];
		
		[self addGroundTarget];
		[self addFlyingTarget];
		
		[self addGroundTarget];
		[self addGroundTarget];
		[self addGroundTarget];
		*/
		[self scheduleUpdate];
	}
	return self;
}

-(void) generateRandomMoveForTarget:(CCSprite*)targetSprite{
	
}
-(void) addGroundTarget{
	CCSprite *targetSprite = [CCSprite spriteWithFile:@"target.png"];
	
	Vector *vector = terrain->GetRandomTargetPosition(targetSprite.contentSize.width, targetSprite.contentSize.height);
	Target *target = new Target(vector->x, vector->y, targetSprite.contentSize.width, targetSprite.contentSize.height);
	targetSprite.position = ccp(target->getPositionX(), target->getPositionY());
	
	TargetObjC *targetObjC = [TargetObjC alloc];
	[targetObjC setTargetData:target :targetSprite];


	[self addChild:targetSprite];
	[targetsArray addObject:targetObjC];

	
}
-(void)addFlyingTarget{
	CCSprite *targetSprite = [CCSprite spriteWithFile:@"target_with_wings.png"];

	Vector *vector = terrain->GetRandomFlyingStartPosition(targetSprite.contentSize.width, targetSprite.contentSize.height);
	
	FlyingTarget *target = new FlyingTarget(0, vector->y, targetSprite.contentSize.width, targetSprite.contentSize.height);
	targetSprite.position = ccp(target->getPositionX(), target->getPositionY());

	FlyingTargetObjC *targetObjC = [FlyingTargetObjC alloc];
	[targetObjC setTargetData:target :targetSprite];

	
	// Determine speed of the monster
	int minDuration = 7.0; //2.0;
	int maxDuration = 10.0; //4.0;
	int rangeDuration = maxDuration - minDuration;
	int actualDuration = (arc4random() % rangeDuration) + minDuration;
	
	// Create the actions
	/*
	CCMoveTo * actionMove = [CCMoveTo actionWithDuration:actualDuration position:ccp((terrain->width + targetSprite.contentSize.width/2), targetSprite.position.y)];
	CCCallBlockN * actionMoveDone = [CCCallBlockN actionWithBlock:^(CCNode *node) {
		[targetsArray removeObject:node];
		[node removeFromParentAndCleanup:YES];
		
	}];
	
	[targetSprite runAction:[CCSequence actions:actionMove, actionMoveDone, nil]];
	*/

	[self addChild:targetSprite];

	[targetsArray addObject:targetObjC];

}


-(void) update: (ccTime) dt
{

	//It is recommended that a fixed time step is used with Box2D for stability
	//of the simulation, however, we are using a variable time step here.
	//You need to make an informed choice, the following URL is useful
	//http://gafferongames.com/game-physics/fix-your-timestep/
	
	//int32 velocityIterations = 8;
	//int32 positionIterations = 1;
	
	// Instruct the world to perform a single step of simulation. It is
	// generally best to keep the time step and iterations fixed.
	
	//world->Step(dt, velocityIterations, positionIterations);
	for(int j = 0; j < [targetsArray count]; j++) {
		TargetObjC *currTargetObj = [targetsArray objectAtIndex:j];

		[currTargetObj update];
	}
	
	
	NSMutableArray *deleteArrows = [[NSMutableArray alloc] init];
	int countHitTarget = 0;
	for (int i = 0; i < [arrowsArray count]; i++) {
		
		ArrowsObjC *currArrowObjC = [arrowsArray objectAtIndex:i];
		CCSprite *currArrowSprite = currArrowObjC.sprite;
	

		if(currArrowSprite.position.x > terrain->width || currArrowSprite.position.y > terrain->height || currArrowSprite.position.x < 0 || currArrowSprite < 0){
			//[arrowsArray removeObject:currArrowObjC];
			//[self removeChild:currArrowSprite cleanup:YES];
			//currArrowObjC = nil;
			//break;
			//[deleteArrows addObject:currArrowObjC];
		
		}else{
			//[currArrowObjC update];
		}
		[currArrowObjC update];
		
	
		NSMutableArray *deleteTargets = [[NSMutableArray alloc] init];

		for(int j = 0; j < [targetsArray count]; j++) {
			TargetObjC *currTargetObj = [targetsArray objectAtIndex:j];
						
			if([currTargetObj collidesWith:((int)(currArrowObjC.arrowC->getPositionX() + currArrowSprite.contentSize.width/2)) :((int)(currArrowObjC.arrowC->getPositionY() + currArrowSprite.contentSize.height/2))]){
			//if (CGRectIntersectsRect(currArrowSprite.boundingBox, currTargetObj.sprite.boundingBox)) {
			

				//[targetsArray removeObject:currTargetObj];
				//[self removeChild:currTargetObj.sprite cleanup:YES];
				//currTargetObj = nil;
				[deleteTargets addObject:currTargetObj];
				countHitTarget += 1;
			}
		
		}
		
		if([deleteTargets count] > 0){
			for(int j = 0; j < [deleteTargets count]; j++) {
				TargetObjC *targetToDelete = [deleteTargets objectAtIndex:j];
				
				[targetsArray removeObject:targetToDelete];
				[self removeChild:targetToDelete.sprite cleanup:YES];
			
			}
		}
	}
	
	
	
	if([deleteArrows count] > 0 ){
		for(int j = 0; j < [deleteArrows count]; j++) {
			ArrowsObjC *arrowToDelete = [deleteArrows objectAtIndex:j];
			[arrowsArray removeObject:arrowToDelete];
			[self removeChild:arrowToDelete.sprite cleanup:YES];


		}
	}
	if(has_touched){
		touching_time += dt;
	}
	NSLog(@"%i", countHitTarget);
	for(int i = 0; i<countHitTarget; i++){
		
		//[self addFlyingTarget];
		[self addFlyingTarget];
	}
}

// Touch first detected
-(void) ccTouchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
	if (!has_touched) touching_time = 0.0;
	has_touched = true;
	
}

// User took finger off screen
- (void)ccTouchesEnded:(NSSet*)touches withEvent:(UIEvent*)event{
	UITouch *touch = [ touches anyObject];
	
	CGPoint new_location = [touch locationInView: [touch view]];
	new_location = [[CCDirector sharedDirector] convertToGL:new_location];

	Arrow *newArrow = new Arrow(_bowSprite.position.x, _bowSprite.position.y);

	ArrowsObjC *arrowObjC = [[ArrowsObjC alloc] init];
	[arrowsArray addObject:arrowObjC];
	
	CCSprite *arrowSprite = [CCSprite spriteWithFile:@"arrow.png"];
	arrowSprite.position = ccp(newArrow->getPositionX(), newArrow->getPositionY());
	
	[self addChild:arrowSprite];
	[arrowObjC setArrowData:newArrow:arrowSprite];
		
	[arrowObjC shootArrow:(int)new_location.x :(int)new_location.y :touching_time*10.0];

	has_touched = false;

}




-(void) dealloc
{
	delete world;
	world = NULL;
	
	delete m_debugDraw;
	m_debugDraw = NULL;
	
	[super dealloc];
}

/*
-(void) initPhysics
{
		
	b2Vec2 gravity;
	gravity.Set(0.0f, -10.0f);
	world = new b2World(gravity);
	
	
	// Do we want to let bodies sleep?
	world->SetAllowSleeping(true);
	
	world->SetContinuousPhysics(true);
	
	m_debugDraw = new GLESDebugDraw( PTM_RATIO );
	world->SetDebugDraw(m_debugDraw);
	
	uint32 flags = 0;
	flags += b2Draw::e_shapeBit;
	//		flags += b2Draw::e_jointBit;
	//		flags += b2Draw::e_aabbBit;
	//		flags += b2Draw::e_pairBit;
	//		flags += b2Draw::e_centerOfMassBit;
	m_debugDraw->SetFlags(flags);
	
	
	// Define the ground body.
	b2BodyDef groundBodyDef;
	groundBodyDef.position.Set(0, 0); // bottom-left corner
	
	// Call the body factory which allocates memory for the ground body
	// from a pool and creates the ground box shape (also from a pool).
	// The body is also added to the world.
	b2Body* groundBody = world->CreateBody(&groundBodyDef);
	
	// Define the ground box shape.
	b2EdgeShape groundBox;
	
	// bottom
	
	groundBox.Set(b2Vec2(0,0), b2Vec2(terrain->width/PTM_RATIO,0));
	groundBody->CreateFixture(&groundBox,0);
	
	// top
	groundBox.Set(b2Vec2(0,terrain->height/PTM_RATIO), b2Vec2(terrain->width/PTM_RATIO,terrain->height/PTM_RATIO));
	groundBody->CreateFixture(&groundBox,0);
	
	// left
	groundBox.Set(b2Vec2(0,terrain->height/PTM_RATIO), b2Vec2(0,0));
	groundBody->CreateFixture(&groundBox,0);
	
	// right
	groundBox.Set(b2Vec2(terrain->width/PTM_RATIO,terrain->height/PTM_RATIO), b2Vec2(terrain->width/PTM_RATIO,0));
	groundBody->CreateFixture(&groundBox,0);
}
#pragma mark GameKit delegate

-(void) achievementViewControllerDidFinish:(GKAchievementViewController *)viewController
{
	AppController *app = (AppController*) [[UIApplication sharedApplication] delegate];
	[[app navController] dismissModalViewControllerAnimated:YES];
}

-(void) leaderboardViewControllerDidFinish:(GKLeaderboardViewController *)viewController
{
	AppController *app = (AppController*) [[UIApplication sharedApplication] delegate];
	[[app navController] dismissModalViewControllerAnimated:YES];
}
*/
@end
