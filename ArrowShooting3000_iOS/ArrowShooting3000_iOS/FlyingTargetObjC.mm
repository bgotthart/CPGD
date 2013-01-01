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

-(void)setTargetData:(FlyingTarget *)object :(CCSprite *)sprite{
	self.target = object;
	self.sprite = sprite;
}

-(void)update{
	self.target->update();
	self.sprite.position = ccp((self.target)->getPositionX(), (self.target)->getPositionY());
}
-(bool)collidesWith:(int) arrowX :(int) arrowY
{

	return self.target->colidesWith(arrowX, arrowY);

}
@end
