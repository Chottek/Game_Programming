#ifndef GEOMETRICWARS_POWERUP_H
#define GEOMETRICWARS_POWERUP_H


#include <SDL_render.h>
#include "Player.h"

class PowerUp {

public:
    PowerUp(SDL_Renderer* renderer, float x, float y, int type);
    ~PowerUp();

    void update();
    void render();

    void gather();

    const SDL_Rect &getBounds() const;

    int getLife() const;

    void updateBounds();

    void setOffsets(float offX, float offY){
        xOffset = offX;
        yOffset = offY;
    }

    float getX() const;

    float getY() const;

private:
    float x, y;
    float xOffset, yOffset;
    int type;
    bool wasSeenByPlayer;
    double life;

    SDL_Texture* texture;
    SDL_Rect bounds;
    SDL_Renderer* renderer;

};


#endif //GEOMETRICWARS_POWERUP_H
