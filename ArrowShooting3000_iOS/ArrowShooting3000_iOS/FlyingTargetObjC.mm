//
//  TargetObjC.m
//  ArrowShooting3000_iOS
//
//  Created by Bianca Gotthart on 12/31/12.
//
//

#import "FlyingTargetObjC.h"

@implementation FlyingTargetObjC
@synthesize target = _target;
@synthesize sprite = _sprite;

-(void)setTargetData:(FlyingTarget *)object :(CCSprite *)sprite :(CCSpriteBatchNode*)spriteSheet{
	self.target = object;
	self.sprite = sprite;
	self.spriteSheet = spriteSheet;
	
	self.sprite.anchorPoint = ccp(0,1);
	
	CGSize winSize = [CCDirector sharedDirector].winSize;
	
	self.target->setPositionY(winSize.height - self.target->getPositionY());
	

	
}

-(void)update{
	CGSize winSize = [CCDirector sharedDirector].winSize;

	self.target->update();
	
	self.sprite.position = ccp((self.target)->getPositionX(), winSize.height - (self.target)->getPositionY());
}
-(bool)collidesWith:(int) arrowX :(int) arrowY
{

	return self.target->colidesWith(arrowX, arrowY);

}
@end
