#import "ArrowsObjC.h"
/** Degrees to Radian **/
#define degreesToRadians( degrees ) ( ( degrees ) / 180.0 * M_PI )
/** Radians to Degrees **/
#define radiansToDegrees( radians ) ( ( radians ) * ( 180.0 / M_PI ) )

@implementation ArrowsObjC

-(void)setArrowData:(Arrow*)object:(CCSprite*)sprite{
	self.arrowC = object;
	self.sprite = sprite;
		
	CGSize winSize = [CCDirector sharedDirector].winSize;

	self.arrowC->setPositionY(winSize.height - (self.arrowC->getPositionY() - self.sprite.contentSize.height/2));
	self.arrowC->setPositionX(self.arrowC->getPositionX() - self.sprite.contentSize.width/2);
	
	//self.sprite.position = ccp(self.arrowC->getPositionX() + self.sprite.contentSize.width/2, winSize.height - (self.arrowC->getPositionY()+(self.sprite.contentSize.height/2)));
	//int offset = -55;
	//self.sprite.position = ccp([self getPositionOfSpriteX:offset], self.sprite.position.y);
	
	/*int x = [self getPositionOfSpriteX:offset];

	self.sprite.position = ccp(x, self.sprite.position.y);
	 */
}
	
-(void)dealloc{
	self.arrowC=nil;
	[super dealloc];
}
-(void)update:(float)arrowOffset :(ccTime)dt{
	
	self.arrowC->update((float)dt);
	

	CGSize winSize = [CCDirector sharedDirector].winSize;


	self.sprite.position = ccp(self.arrowC->getPositionX() + self.sprite.contentSize.width/2,
														 winSize.height - (self.arrowC->getPositionY()+(self.sprite.contentSize.height/2)));

	self.sprite.position = ccp([self getPositionOfSpriteX:arrowOffset], self.sprite.position.y);

	self.sprite.rotation = (float)radiansToDegrees(self.arrowC->getRotation());
}

-(void) shootArrow:(int) mouseX :(int) mouseY :(int) bowCenterX :(int) bowCenterY {
	CGSize winSize = [CCDirector sharedDirector].winSize;

	
	self.arrowC->shootArrow(mouseX, winSize.height -  mouseY, bowCenterX, bowCenterY);
	
}

-(void)getTouchRotation:(float) mouseX :(float) mouseY :(int)arrowOffset :(int) bowCenterX :(int) bowCenterY {
	
	CGSize winSize = [CCDirector sharedDirector].winSize;
	self.sprite.anchorPoint = ccp(((self.sprite.contentSize.width/2) - arrowOffset)/self.sprite.contentSize.width, 0.5);

	
	self.sprite.rotation = (float)radiansToDegrees(self.arrowC->getTouchRotation(mouseX, winSize.height - mouseY, bowCenterX, bowCenterY));

}


-(int)getPositionOfSpriteX:(int)arrowOffset{
	return self.sprite.position.x - arrowOffset;

}

@end
