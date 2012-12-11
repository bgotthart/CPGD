//
//  GameOverLayer.h
//  FirstGame
//
//  Created by Bianca Gotthart on 12/10/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "cocos2d.h"

@interface GameOverLayer : CCLayerColor

+(CCScene *) sceneWithWon:(BOOL)won;
-(id)initWithWon:(BOOL)won;

@end
