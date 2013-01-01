//
//  TargetObjC.m
//  ArrowShooting3000_iOS
//
//  Created by Bianca Gotthart on 12/31/12.
//
//

#import "TargetObjC.h"

@implementation TargetObjC
@synthesize target = _target;
@synthesize sprite = _sprite;

-(void)setTargetData:(Target *)object :(CCSprite *)sprite{
	self.target = object;
	self.sprite = sprite;
}
-(void)update{
	self.target->update();
	self.sprite.position = ccp((self.target)->getPositionX(), (self.target)->getPositionY());
}
@end
