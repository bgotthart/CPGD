#ifndef SCORE_H
#define SCORE_H

#include <cstddef>

class Score{
      public:
             static Score* getInstance();
             int getScore();
             void addScore(int);
             void resetScore();
             
      private:
              Score();
              static Score* theInstance; 
              int score;     
};

#endif
