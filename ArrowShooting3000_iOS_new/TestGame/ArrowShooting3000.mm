//
//  HelloWorldLayer.mm
//  TestGame
//
//  Created by Bianca Gotthart on 1/7/13.
//  Copyright __MyCompanyName__ 2013. All rights reserved.
//

// Import the interfaces
#import "ArrowShooting3000.h"

// Needed to obtain the Navigation Controller
#import "AppDelegate.h"

#import "ArrowsObjC.h"
#import "FlyingTargetObjC.h"
#import "score.h"
#import "FinishLayer.h"
#define radiansToDegrees( radians ) ( ( radians ) * ( 180.0 / M_PI ) )

@implementation ArrowShooting3000

@synthesize arrowHud = _arrowHud;
@synthesize greenSprite = _greenSprite;
@synthesize redSprite = _redSprite;
@synthesize player = _player;
@synthesize terrain = terrain;
@synthesize bow = _bow;
@synthesize bowAction = _bowAction;

NSMutableArray *arrowsArray;
NSMutableArray *targetsArray;

ArrowsObjC *arrowObjC;
Score *score;
CCLabelTTF * scoreLabel;
bool has_touched = false;
float touching_time;
float flyingTargetTimer;
float flyingTargetSpawnTime;
int arrowOffset = -55;
int arrowSteps = 0;
float arrowTimer = 0;
float arrowUpdateTime = 0.1f;
ccTime globalTimer = 3600;
CCAnimation *bowAnim;
CCAnimation *flyAnim;

+(CCScene *) scene
{
	CCScene *scene = [CCScene node];
	ArrowShooting3000 *layer = [ArrowShooting3000 node];
	[scene addChild: layer];
	return scene;
}


-(id) init
{
	if( (self=[super init])) {
		
		globalTimer = 3600;
		self.isTouchEnabled = YES;

		score = Score::getInstance();

		[self addBowAnimation];
		
		[self addElements];
		
		[self scheduleUpdate];
		
		NSString *message = [NSString stringWithFormat:@"Score: %i | Time: %i", score->getScore(), (int)globalTimer/60];
		
		scoreLabel = [CCLabelTTF labelWithString:message fontName:@"Courier New" fontSize:16];
		scoreLabel.color = ccc3(255,255,255);
		scoreLabel.position = ccp((scoreLabel.contentSize.width/2+20), 20);
		[self updateScoreMenu];

		[self addChild:scoreLabel];

	}
	return self;
}
-(void) addBowAnimation{
	[[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFramesWithFile:@"bow_animation_default.plist"];
	
	CCSpriteBatchNode *spriteSheet = [CCSpriteBatchNode batchNodeWithFile:@"bow_animation_default.png"];
	[self addChild:spriteSheet];
	
	NSMutableArray *walkAnimFrames = [NSMutableArray array];
	for(int i = 1; i <= 4; ++i) {
		[walkAnimFrames addObject:[[CCSpriteFrameCache sharedSpriteFrameCache] spriteFrameByName:[NSString stringWithFormat:@"bow_%d.png", i]]];
	}
	bowAnim = [CCAnimation animationWithFrames:walkAnimFrames delay:0.05f];
	
	bowAnim.loops = 1;
	
	self.bow = [CCSprite spriteWithSpriteFrameName:@"bow_1.png"];
	CGSize winSize = [CCDirector sharedDirector].winSize;

	
	
	self.player = new Player(winSize.width - _bow.contentSize.width/2, _bow.contentSize.height/2);
	
	_bow.position = ccp(self.player->getPositionX(), self.player->getPositionY());
	
	self.bowAction = [CCAnimate actionWithAnimation:bowAnim restoreOriginalFrame:NO];

	[spriteSheet addChild:_bow];

	self.terrain = new Terrain(winSize.width, winSize.height, self.player->getPositionX(), _bow.position.y + _bow.contentSize.height/2);


}

-(void) addGroundTarget{
	CCSprite *targetSprite = [CCSprite spriteWithFile:@"target.png"];
	
	Vector *vector = self.terrain->GetRandomTargetPositionIOS(targetSprite.contentSize.width, targetSprite.contentSize.height);
	Target *target = new Target(vector->x, vector->y, targetSprite.contentSize.width, targetSprite.contentSize.height);
	targetSprite.position = ccp(target->getPositionX(), target->getPositionY());
	
	TargetObjC *targetObjC = [TargetObjC alloc];
	[targetObjC setTargetData:target :targetSprite];
	
	
	[self addChild:targetSprite];
	[targetsArray addObject:targetObjC];
	
	
}
-(void) addFlyingAnimation{
	
	
	[[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFramesWithFile:@"target_with_wings_animation_default.plist"];
	
	CCSpriteBatchNode *spriteSheet = [CCSpriteBatchNode batchNodeWithFile:@"target_with_wings_animation_default.png"];
	[self addChild:spriteSheet];
	
	NSMutableArray *flyingAnimFrames = [NSMutableArray array];
	for(int i = 1; i <= 6; ++i) {
		[flyingAnimFrames addObject:[[CCSpriteFrameCache sharedSpriteFrameCache] spriteFrameByName:[NSString stringWithFormat:@"target_%d.png", i]]];
	}
	CCAnimation *flyingAnim = [CCAnimation animationWithFrames:flyingAnimFrames delay:0.1f];
	
	CCSprite *targetSprite = [CCSprite spriteWithSpriteFrameName:@"target_1.png"];
	Vector *vector = self.terrain->GetRandomFlyingStartPositionIOS(targetSprite.contentSize.width, targetSprite.contentSize.height);
	
	FlyingTarget *target = new FlyingTarget(vector->x, vector->y, targetSprite.contentSize.width, targetSprite.contentSize.height);
	targetSprite.position = ccp(target->getPositionX(), target->getPositionY());
	
	FlyingTargetObjC *targetObjC = [FlyingTargetObjC alloc];
	[targetObjC setTargetData:target :targetSprite];

	CCAction *flyingAction = [CCRepeatForever actionWithAction:[CCAnimate actionWithAnimation:flyingAnim restoreOriginalFrame:NO]];
	[spriteSheet addChild:targetSprite];
	
	[targetSprite runAction:flyingAction];
	
	
	[targetsArray addObject:targetObjC];


}
-(void)addElements{
	
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
	
	arrowsArray = [[NSMutableArray alloc] init];
	targetsArray = [[NSMutableArray alloc] init];

	for(int i = 0; i<5; i++){
		int random = terrain->GetRandomValue();
		if(random == 0)
			[self addGroundTarget];
		else
			[self addFlyingAnimation];
	}
	
	
}

-(void) update: (ccTime) dt
{
	
	[self updateScoreMenu];
	
		for(int j = 0; j < [targetsArray count]; j++) {
		TargetObjC *currTargetObj = [targetsArray objectAtIndex:j];
		
		[currTargetObj update];
	}
	
	
	NSMutableArray *deleteArrows = [[NSMutableArray alloc] init];
	int countHitTarget = 0;
	for (int i = 0; i < [arrowsArray count]; i++) {
		
		ArrowsObjC *currArrowObjC = [arrowsArray objectAtIndex:i];
		CCSprite *currArrowSprite = currArrowObjC.sprite;

		
		[currArrowObjC update:arrowOffset :dt*1000];
		
		
		NSMutableArray *deleteTargets = [[NSMutableArray alloc] init];
		
		for(int j = 0; j < [targetsArray count]; j++) {
			TargetObjC *currTargetObj = [targetsArray objectAtIndex:j];
			
			if([currTargetObj collidesWith:((int)(currArrowObjC.arrowC->getPositionX() + currArrowSprite.contentSize.width/2)) :((int)(currArrowObjC.arrowC->getPositionY() + currArrowSprite.contentSize.height/2))]){

				[deleteTargets addObject:currTargetObj];
				countHitTarget += 1;
			}	
		}
		
		if([deleteTargets count] > 0){
			for(int j = 0; j < [deleteTargets count]; j++) {
				TargetObjC *targetToDelete = [deleteTargets objectAtIndex:j];
				
				
				if([targetToDelete isKindOfClass:[FlyingTargetObjC class]]){
					[targetToDelete.sprite stopAllActions];
					
					[targetToDelete.sprite  removeFromParentAndCleanup: YES];
					[self removeChild:((FlyingTargetObjC*)targetToDelete).spriteSheet cleanup:YES];
					
				}else{
					[self removeChild:targetToDelete.sprite cleanup:YES];
				}
				
				[targetsArray removeObject:targetToDelete];

				
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
	
	for(int i = 0; i<countHitTarget; i++){
		
		int random = terrain->GetRandomValue();
		if(random == 0)
			[self addGroundTarget];
		else
			[self addFlyingAnimation];
		
	}

	if(has_touched){
		float scale = self.arrowHud->getCurrentScale(arrowObjC.arrowC->getStrength());
		[self.greenSprite setScaleX: scale];
	
		arrowTimer += dt;
		if(arrowTimer >= arrowUpdateTime){
			if(arrowSteps < 4){
				arrowOffset += 9;
			
				arrowObjC.sprite.anchorPoint = ccp(((arrowObjC.sprite.contentSize.width/2) - arrowOffset)/arrowObjC.sprite.contentSize.width, 0.5);
			
				arrowSteps += 1;

			}

		}
	
	}
	if(globalTimer < 0){
		[self showFinishLayer];
	}
	globalTimer--;


}
 
-(void)updateScoreMenu{
	NSString *message = [NSString stringWithFormat:@"Score: %i | Time: %i", score->getScore(), (int)globalTimer/60];
	
	[scoreLabel setString:message];
	
}
-(void) showFinishLayer{
	CCScene *finishScene = [FinishLayer sceneWithFinish:globalTimer];
	[[CCDirector sharedDirector] replaceScene:finishScene];

}

-(void) registerWithTouchDispatcher
{

	[[CCTouchDispatcher sharedDispatcher] addTargetedDelegate:self priority:0
																						swallowsTouches:NO];

}

-(BOOL) ccTouchBegan:(UITouch *)touch withEvent:(UIEvent *)event {
	
	CGPoint new_location = [touch locationInView: [touch view]];
	new_location = [[CCDirector sharedDirector] convertToGL:new_location];
	
	if(!has_touched){
		
		Arrow *newArrow = new Arrow(_bow.position.x + arrowOffset, _bow.position.y);
		
		arrowObjC = [[ArrowsObjC alloc] init];
		
		CCSprite *arrowSprite = [CCSprite spriteWithFile:@"arrow.png"];
		arrowSprite.position = ccp(newArrow->getPositionX(), newArrow->getPositionY());
		
		[self addChild:arrowSprite];
		[arrowObjC setArrowData:newArrow:arrowSprite];

		CGPoint new_location = [touch locationInView: [touch view]];
		new_location = [[CCDirector sharedDirector] convertToGL:new_location];

		int bowCenterX = 	_bow.position.x;
		int bowCenterY = terrain->height - _bow.position.y;
		
		[arrowObjC getTouchRotation:new_location.x :new_location.y :arrowOffset :bowCenterX :bowCenterY ];
		
		arrowObjC.sprite.position = ccp([arrowObjC getPositionOfSpriteX:arrowOffset], arrowObjC.sprite.position.y);

		
		arrowObjC.arrowC->startArrow();
	
	
		_bow.rotation = (float)radiansToDegrees(arrowObjC.arrowC->getTouchRotation(new_location.x, self.terrain->height - new_location.y, bowCenterX, bowCenterY));
		[_bow stopAllActions];
		[_bow runAction:_bowAction];

	}
	
	has_touched = true;
	arrowSteps = 0;
	
	return YES;
}

-(void) ccTouchMoved:(UITouch *)touch withEvent:(UIEvent *)event {

	CGPoint new_location = [touch locationInView: [touch view]];
	new_location = [[CCDirector sharedDirector] convertToGL:new_location];

	int bowCenterX = 	_bow.position.x;
	int bowCenterY = terrain->height - _bow.position.y;
	
	[arrowObjC getTouchRotation:new_location.x :new_location.y :arrowOffset :bowCenterX :bowCenterY ];
	
	_bow.rotation = (float)radiansToDegrees(arrowObjC.arrowC->getTouchRotation(new_location.x, self.terrain->height - new_location.y, bowCenterX, bowCenterY));
	
}
// User took finger off screen
-(void) ccTouchEnded:(UITouch *)touch withEvent:(UIEvent *)event {
	
	CGPoint new_location = [touch locationInView: [touch view]];
	new_location = [[CCDirector sharedDirector] convertToGL:new_location];

	int bowCenterX = 	_bow.position.x;
	int bowCenterY = terrain->height - _bow.position.y;

	
	[arrowObjC shootArrow:new_location.x :new_location.y :bowCenterX :bowCenterY ];
	
	[arrowsArray addObject:arrowObjC];
	
	
	[self.greenSprite setScaleX:0];
	
	[_bow stopAction:_bowAction];
	
	CCSpriteFrame *frame = [[CCSpriteFrameCache sharedSpriteFrameCache] spriteFrameByName:@"bow_1.png"];

	[_bow setDisplayFrame:frame];
	
	has_touched = false;
	arrowOffset = -55;
	arrowTimer = 0;
	
}

-(void) dealloc
{
	delete world;
	world = NULL;
		
	[super dealloc];
}


@end
