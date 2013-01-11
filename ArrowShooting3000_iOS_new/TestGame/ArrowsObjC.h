//
//  ArrowObjC.h
//  ArrowShooting3000_iOS
//
//  Created by Bianca Gotthart on 12/30/12.
//
//

#import <Foundation/Foundation.h>
#import "arrow.h"
#import "cocos2d.h"

@interface ArrowsObjC : NSObject

@property Arrow* arrowC;
@property (assign) CCSprite *sprite;
-(void)setArrowData:(Arrow*)arrowC:(CCSprite*)sprite;
-(void)update:(float)arrowOffset :(ccTime) dt;
-(void)shootArrow:(int)x :(int)y ;
-(void)getTouchRotation:(float) mouseX :(float) mouseY :(int)arrowOffset;
-(void)setPositionY:(float) y;
-(void)setPositionX:(float) x;
-(int)getPositionOfSpriteX:(int)arrowOffset;

@end
