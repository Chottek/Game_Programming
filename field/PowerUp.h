#ifndef GEOMETRICWARS_POWERUP_H
#define GEOMETRICWARS_POWERUP_H


#include <SDL_render.h>

class PowerUp {

public:
    PowerUp(SDL_Renderer* renderer, float x, float y, int type);
    ~PowerUp();

    void update();
    void render();

    void gather();

    const SDL_Rect &getBounds() const;

    int getLife() const;

private:
    float x, y;
    int type;
    bool wasSeenByPlayer;
    int life;

    SDL_Texture* texture;
    SDL_Rect bounds;
    SDL_Renderer* renderer;

};


#endif //GEOMETRICWARS_POWERUP_H
