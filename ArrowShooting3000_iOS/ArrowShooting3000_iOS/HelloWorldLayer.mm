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
bool has_touched = false;
 
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
	NSLog(@"%s", __PRETTY_FUNCTION__);
	if( (self=[super init])) {
		
		// enable events
		
		self.isTouchEnabled = YES;
		self.isAccelerometerEnabled = YES;
		CGSize winSize = [CCDirector sharedDirector].winSize;
		
		// init physics
		[self initPhysics];
		
		// create reset button
		//[self createMenu];
		
		//Set up sprite
		
		_bowSprite = [CCSprite spriteWithFile:@"bow.png"];

		player = new Player(winSize.width - _bowSprite.contentSize.width/2, winSize.height/2);
		//_arrowSprite = [CCSprite spriteWithFile:@"arrow.png"];
		//_arrowSprite.position = ccp(player->getPositionX(),player->getPositionY());
		//[self addChild:_arrowSprite];
	
		_bowSprite.position = ccp(player->getPositionX(),player->getPositionY());
		
		arrowsArray = [[NSMutableArray alloc] init];
		
		[self addChild:_bowSprite];
		[self scheduleUpdate];
	}
	return self;
}


-(void) draw
{
	//
	// IMPORTANT:
	// This is only for debug purposes
	// It is recommend to disable it
	//
	[super draw];
	
	ccGLEnableVertexAttribs( kCCVertexAttribFlag_Position );
	
	kmGLPushMatrix();
	
	world->DrawDebugData();	
	
	kmGLPopMatrix();
}


-(void) update: (ccTime) dt
{

	//It is recommended that a fixed time step is used with Box2D for stability
	//of the simulation, however, we are using a variable time step here.
	//You need to make an informed choice, the following URL is useful
	//http://gafferongames.com/game-physics/fix-your-timestep/
	
	int32 velocityIterations = 8;
	int32 positionIterations = 1;
	
	// Instruct the world to perform a single step of simulation. It is
	// generally best to keep the time step and iterations fixed.
	world->Step(dt, velocityIterations, positionIterations);

	for (int i = 0; i < [arrowsArray count]; i++) {
		
		ArrowsObjC *curr = [arrowsArray objectAtIndex:i];
		Arrow* arrow = (Arrow*)curr.object;
		arrow->update();
		CCSprite* sprite = (CCSprite *)curr.sprite;
		sprite.position = ccp(((Arrow*)curr.object)->getPositionX(), ((Arrow*)curr.object)->getPositionY());
		sprite.rotation = (((Arrow*)curr.object)->getRotation())*M_PI / 180;
//		CCSprite* sprite = [CCSprite spriteWithFile:@"arrow.png"];
		
//		sprite.position = ccp(arrow->getPositionX(), arrow->getPositionY());
		
		//[self addChild:sprite];
    //Arrow* currArrow = ( Arrow*)[arrowsArray objectAtIndex:i];
		
		//currArrow->update();
	}
	
	//todo touch question: time
}

// Touch first detected
-(void) ccTouchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
	has_touched = true;
}
// Touches moved
-(void) ccTouchesMoved:(NSSet *)touches withEvent:(UIEvent *)event{
	if(!has_touched){
		return;
	}
	UITouch *touch = [ touches anyObject];
	
	CGPoint new_location = [touch locationInView: [touch view]];
	new_location = [[CCDirector sharedDirector] convertToGL:new_location];
	
	//arrow->update();
	
	
}
// User took finger off screen
- (void)ccTouchesEnded:(NSSet*)touches withEvent:(UIEvent*)event{
	UITouch *touch = [ touches anyObject];
	
	CGPoint new_location = [touch locationInView: [touch view]];
	new_location = [[CCDirector sharedDirector] convertToGL:new_location];
	
	Arrow *newArrow = new Arrow(_bowSprite.position.x, _bowSprite.position.y);
	
	ArrowsObjC *arrowObj = [[ArrowsObjC alloc] init];
	[arrowsArray addObject:arrowObj];
	
	CCSprite *arrowSprite = [CCSprite spriteWithFile:@"arrow.png"];
	arrowSprite.position = ccp(newArrow->getPositionX(),newArrow->getPositionY());
	
	[self addChild:arrowSprite];
	[arrowObj setArrowData:newArrow:arrowSprite];

	
	newArrow->shootArrow((int)new_location.x, (int)new_location.y, 1.0);

	//[arrowObj dealloc];
	has_touched = false;

}
// Touch was somehow interrupted
- (void)ccTouchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event{
	
}



-(void) dealloc
{
	delete world;
	world = NULL;
	
	delete m_debugDraw;
	m_debugDraw = NULL;
	
	[super dealloc];
}


-(void) initPhysics
{
	
	CGSize s = [[CCDirector sharedDirector] winSize];
	
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
	
	groundBox.Set(b2Vec2(0,0), b2Vec2(s.width/PTM_RATIO,0));
	groundBody->CreateFixture(&groundBox,0);
	
	// top
	groundBox.Set(b2Vec2(0,s.height/PTM_RATIO), b2Vec2(s.width/PTM_RATIO,s.height/PTM_RATIO));
	groundBody->CreateFixture(&groundBox,0);
	
	// left
	groundBox.Set(b2Vec2(0,s.height/PTM_RATIO), b2Vec2(0,0));
	groundBody->CreateFixture(&groundBox,0);
	
	// right
	groundBox.Set(b2Vec2(s.width/PTM_RATIO,s.height/PTM_RATIO), b2Vec2(s.width/PTM_RATIO,0));
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

@end
