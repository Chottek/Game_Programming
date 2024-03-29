#ifndef GEOMETRICWARS_FIELD_H
#define GEOMETRICWARS_FIELD_H

#include <SDL_render.h>

class Field {

public:
    Field(SDL_Renderer* renderer);
    ~Field();

    bool init();

    void update();
    void render();

    bool getPlayerAlive();

private:

    void generateEnemy();

    SDL_Renderer* renderer;
    SDL_Rect bcgRect;
    SDL_Texture* bcgTexture;
};


#endif //GEOMETRICWARS_FIELD_H
