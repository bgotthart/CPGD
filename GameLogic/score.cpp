#include "score.h"

Score* Score::theInstance = NULL;

Score* Score::getInstance(){
       if(theInstance == NULL){
                      theInstance = new Score();
       }       
       return theInstance;
}

int Score::getScore(){
    return this->score;
}

void Score::addScore(int score){
     this->score += score;    
}

void Score::resetScore(){
     this->score = 0;    
}

Score::Score(){
    this->score = 0;
}
