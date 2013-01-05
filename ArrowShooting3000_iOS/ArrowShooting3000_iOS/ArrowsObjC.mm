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

	self.arrowC->setPositionY(winSize.height - self.arrowC->getPositionY());

}
	
-(void)dealloc{
	self.arrowC=nil;
	[super dealloc];
}
-(void)update{
	
	self.arrowC->update();
	

	CGSize winSize = [CCDirector sharedDirector].winSize;


	self.sprite.position = ccp((int)self.arrowC->getPositionX() + self.sprite.contentSize.width/2, winSize.height - (self.arrowC->getPositionY()+(self.sprite.contentSize.height/2)));
	self.sprite.rotation = (float)radiansToDegrees(self.arrowC->getRotation());
}

-(void) shootArrow:(int) mouseX :(int) mouseY :(float) strength{
	CGSize winSize = [CCDirector sharedDirector].winSize;

	self.arrowC->shootArrow(mouseX, winSize.height -  mouseY , strength);
	
}
@end
