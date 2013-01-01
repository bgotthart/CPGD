//
//  ArrowObjC.m
//  ArrowShooting3000_iOS
//
//  Created by Bianca Gotthart on 12/30/12.
//
//

#import "ArrowsObjC.h"

@implementation ArrowsObjC

-(void)setArrowData:(Arrow*)object:(CCSprite*)sprite{
	self.object = object;
	self.sprite = sprite;
}
	
-(void)dealloc{
	self.object=nil;
	[super dealloc];
}
-(void)update{
	Arrow* arrow = self.object;
	arrow->update();
	self.sprite.position = ccp((self.object)->getPositionX(), (self.object)->getPositionY());
	self.sprite.rotation = ((self.object)->getRotation())*M_PI/180;
	
}
@end
