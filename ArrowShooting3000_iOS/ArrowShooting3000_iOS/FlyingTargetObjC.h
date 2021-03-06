//
//  TargetObjC.h
//  ArrowShooting3000_iOS
//
//  Created by Bianca Gotthart on 12/31/12.
//
//

#import <Foundation/Foundation.h>
#import "target.h"
#import "flyingTarget.h"
#import "cocos2d.h"
#import "TargetObjC.h"
@interface FlyingTargetObjC : TargetObjC

@property FlyingTarget* target;
@property (assign) CCSprite *sprite;
@property (assign) CCSpriteBatchNode* spriteSheet;
-(void)setTargetData:(FlyingTarget*)object:(CCSprite*)sprite:(CCSpriteBatchNode*)spriteSheet;
-(bool)collidesWith:(int) arrowX :(int) arrowY;
-(void)update;
@end
