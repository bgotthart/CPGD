//
//  TargetObjC.h
//  ArrowShooting3000_iOS
//
//  Created by Bianca Gotthart on 12/31/12.
//
//

#import <Foundation/Foundation.h>
#import "target.h"
#import "groundTarget.h"
#import "flyingTarget.h"
#import "cocos2d.h"

@interface TargetObjC : NSObject

@property Target* target;
@property (assign) CCSprite *sprite;
-(void)setTargetData:(Target*)object:(CCSprite*)sprite;
-(void)update;

@end
