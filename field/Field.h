#ifndef GEOMETRICWARS_FIELD_H
#define GEOMETRICWARS_FIELD_H

#include "Player.h"

class Field {

public:
    Field(SDL_Renderer* renderer);
    ~Field();
    void update();
    void render();

private:
    SDL_Renderer* renderer;
    SDL_Rect bcgRect;
    SDL_Texture* bcgTexture;
};


#endif //GEOMETRICWARS_FIELD_H
