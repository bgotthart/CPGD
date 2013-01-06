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

#import "ArrowsObjC.h"
#import "FlyingTargetObjC.h"


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
@synthesize arrowHud = _arrowHud;
@synthesize greenSprite = _greenSprite;
@synthesize redSprite = _redSprite;
@synthesize player = _player;
@synthesize terrain = terrain;

NSMutableArray *arrowsArray;
NSMutableArray *targetsArray;
ArrowsObjC *arrowObjC;

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


@synthesize bear = _bear;
@synthesize walkAction = _walkAction;


-(id) init
{
	if( (self=[super init])) {
		
		
		
		// enable events
		
		self.isTouchEnabled = YES;
		self.isAccelerometerEnabled = YES;
		CGSize winSize = [CCDirector sharedDirector].winSize;
		self.self.terrain = new Terrain(winSize.width, winSize.height);
		/*
			
		[[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFramesWithFile:@"bow_animation_default.plist"];
		
		// Create a sprite sheet with the Happy Bear images
		CCSpriteBatchNode *spriteSheet = [CCSpriteBatchNode batchNodeWithFile:@"bow_animation_default.png"];
		[self addChild:spriteSheet];
		
		// Load up the frames of our animation
		NSMutableArray *walkAnimFrames = [NSMutableArray array];
		for(int i = 1; i <= 4; ++i) {
			[walkAnimFrames addObject:[[CCSpriteFrameCache sharedSpriteFrameCache] spriteFrameByName:[NSString stringWithFormat:@"bow_%d.png", i]]];
		}
		CCAnimation *walkAnim = [CCAnimation animationWithFrames:walkAnimFrames delay:0.1f];
		
		// Create a sprite for our bear
		self.bear = [CCSprite spriteWithSpriteFrameName:@"bow_1.png"];
		_bear.position = ccp(winSize.width/2, winSize.height/2);
		self.walkAction = [CCRepeatForever actionWithAction:[CCAnimate actionWithAnimation:walkAnim restoreOriginalFrame:NO]];
		//[_bear runAction:_walkAction];
		[spriteSheet addChild:_bear];
		*/
		
		// init physics
		//[self initPhysics];
		
		// create reset button
		//[self createMenu];
		
		//Set up sprite
		
		_bow = [CCSprite spriteWithFile:@"bow.png"];
		self.player = new Player(self.terrain->width - _bow.contentSize.width/2, self.terrain->height/2);
		_bow.position = ccp(self.player->getPositionX(),self.player->getPositionY());

		[self addChild:_bow];
		
		
		arrowsArray = [[NSMutableArray alloc] init];
		targetsArray = [[NSMutableArray alloc] init];

		//Timer
		groundTargetSpawnTime = 3;
		flyingTargetSpawnTime = 8;
		
		[self addGroundTarget];
		[self addFlyingTarget];
		
		self.greenSprite = [[CCSprite alloc] initWithFile:@"strength_level_green.png"];
		self.redSprite = [[CCSprite alloc] initWithFile:@"strength_level_red.png"];

		self.greenSprite.anchorPoint = ccp(0,1);
		self.redSprite.anchorPoint = ccp(0,1);

		self.greenSprite.position = ccp(self.terrain->width - self.greenSprite.contentSize.width - 20,self.greenSprite.contentSize.height);
		
		self.redSprite.position = ccp(self.terrain->width - self.redSprite.contentSize.width - 20, self.greenSprite.contentSize.height);
		
		[self addChild:self.redSprite];

		[self addChild:self.greenSprite];
		
		self.arrowHud = new ArrowHud(self.greenSprite.contentSize.width);
		
		[self.greenSprite setScaleX:0];
		
		
		
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
	
	Vector *vector = self.terrain->GetRandomTargetPosition(targetSprite.contentSize.width, targetSprite.contentSize.height);
	Target *target = new Target(vector->x, vector->y, targetSprite.contentSize.width, targetSprite.contentSize.height);
	targetSprite.position = ccp(target->getPositionX(), target->getPositionY());
	
	TargetObjC *targetObjC = [TargetObjC alloc];
	[targetObjC setTargetData:target :targetSprite];


	[self addChild:targetSprite];
	[targetsArray addObject:targetObjC];

	
}
-(void)addFlyingTarget{
	CCSprite *targetSprite = [CCSprite spriteWithFile:@"target_with_wings.png"];

	Vector *vector = self.terrain->GetRandomFlyingStartPosition(targetSprite.contentSize.width, targetSprite.contentSize.height);
	
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
	CCMoveTo * actionMove = [CCMoveTo actionWithDuration:actualDuration position:ccp((self.terrain->width + targetSprite.contentSize.width/2), targetSprite.position.y)];
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
	

		if(currArrowSprite.position.x > self.terrain->width || currArrowSprite.position.y > self.terrain->height || currArrowSprite.position.x < 0 || currArrowSprite < 0){
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
		float scale = self.arrowHud->getCurrentScale(arrowObjC.arrowC->getStrength());
				
		
	//	self.greenSprite.contentSize = CGSizeMake(10, strength);
		[self.greenSprite setScaleX: scale];

		
	}
	for(int i = 0; i<countHitTarget; i++){
		
		//[self addFlyingTarget];
		[self addFlyingTarget];
	}
}

// Touch first detected
-(void) ccTouchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
	//if (!has_touched) touching_time = 0.0;
	
	if(!has_touched){
		
		Arrow *newArrow = new Arrow(_bow.position.x, _bow.position.y);
		
		arrowObjC = [[ArrowsObjC alloc] init];
		
		CCSprite *arrowSprite = [CCSprite spriteWithFile:@"arrow.png"];
		arrowSprite.position = ccp(newArrow->getPositionX(), newArrow->getPositionY());
		
		[self addChild:arrowSprite];
		[arrowObjC setArrowData:newArrow:arrowSprite];

		arrowObjC.arrowC->startArrow();

	}
	
	has_touched = true;
	
	//arrow->addStrengthLevelView
	
	//CCSprite *levelSprite = [CCSprite alloc] init
	

}

-(void)ccTouchesMoved:(NSSet *)touches withEvent:(UIEvent *)event{
	UITouch *touch = [ touches anyObject];
	
	CGPoint new_location = [touch locationInView: [touch view]];
	new_location = [[CCDirector sharedDirector] convertToGL:new_location];

	[arrowObjC getTouchRotation:new_location.x :new_location.y ];

}
// User took finger off screen
- (void)ccTouchesEnded:(NSSet*)touches withEvent:(UIEvent*)event{
	
	UITouch *touch = [ touches anyObject];
	
	CGPoint new_location = [touch locationInView: [touch view]];
	new_location = [[CCDirector sharedDirector] convertToGL:new_location];

		
	[arrowObjC shootArrow:(int)new_location.x :(int)new_location.y ];
	
	[arrowsArray addObject:arrowObjC];

	has_touched = false;
	
	[self.greenSprite setScaleX:0];

}




-(void) dealloc
{
	delete world;
	world = NULL;
	
	delete m_debugDraw;
	m_debugDraw = NULL;
	
	
	self.bear = nil;
	self.walkAction = nil;
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
	
	groundBox.Set(b2Vec2(0,0), b2Vec2(self.terrain->width/PTM_RATIO,0));
	groundBody->CreateFixture(&groundBox,0);
	
	// top
	groundBox.Set(b2Vec2(0,self.terrain->height/PTM_RATIO), b2Vec2(self.terrain->width/PTM_RATIO,self.terrain->height/PTM_RATIO));
	groundBody->CreateFixture(&groundBox,0);
	
	// left
	groundBox.Set(b2Vec2(0,self.terrain->height/PTM_RATIO), b2Vec2(0,0));
	groundBody->CreateFixture(&groundBox,0);
	
	// right
	groundBox.Set(b2Vec2(self.terrain->width/PTM_RATIO,self.terrain->height/PTM_RATIO), b2Vec2(self.terrain->width/PTM_RATIO,0));
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
