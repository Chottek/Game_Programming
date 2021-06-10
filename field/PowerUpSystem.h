#ifndef GEOMETRICWARS_POWERUPSYSTEM_H
#define GEOMETRICWARS_POWERUPSYSTEM_H

#include <SDL_render.h>
#include <list>
#include "PowerUp.h"

class PowerUpSystem {

public:
    PowerUpSystem(SDL_Renderer* renderer);
    ~PowerUpSystem();

    void update();
    void render();

    std::list<PowerUp*> getPowerUps(){ return powerUps; }

    void setOffsets(float xOff, float yOff){
        xOffset = xOff;
        yOffset = yOff;
    }

    float getXOffset() const;

    float getYOffset() const;

    void generate();

private:
    float xOffset, yOffset;
    SDL_Renderer* renderer;
    std::list<PowerUp*> powerUps;

};


#endif //GEOMETRICWARS_POWERUPSYSTEM_H
