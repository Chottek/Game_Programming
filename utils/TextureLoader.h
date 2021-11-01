
#ifndef GEOMETRICWARS_TEXTURELOADER_H
#define GEOMETRICWARS_TEXTURELOADER_H


#pragma once
#include "../Game.h"

class TextureLoader{

public:
    static SDL_Texture* loadTexture(const char* fileName, SDL_Renderer* renderer);


};


#endif //GEOMETRICWARS_TEXTURELOADER_H
