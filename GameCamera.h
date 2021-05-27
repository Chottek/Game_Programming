#ifndef GEOMETRICWARS_GAMECAMERA_H
#define GEOMETRICWARS_GAMECAMERA_H


#include "GameHandler.h"
#include "field/Player.h"

class GameCamera {

public:
    GameCamera(GameHandler *h, float xOff, float yOff){
       handler = h;
       xOffset = xOff;
       yOffset = yOff;
    };

    void center(Player *p){
        xOffset = (p->getX()) - (handler->getGame()->getWidth() / 2);
        yOffset = (p->getY()) - (handler->getGame()->getHeight() / 2);
    }

    void move(float xAmt, float yAmt){
        xOffset += xAmt;
        yOffset += yAmt;
    }

    float getXOffset() const {
        return xOffset;
    }

    float getYOffset() const {
        return yOffset;
    }


private:
    GameHandler* handler;
    float xOffset, yOffset;

};


#endif //GEOMETRICWARS_GAMECAMERA_H
