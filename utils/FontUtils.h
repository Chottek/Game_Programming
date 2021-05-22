#ifndef GEOMETRICWARS_FONTUTILS_H
#define GEOMETRICWARS_FONTUTILS_H
#pragma once

#include <SDL2/SDL_ttf.h>

class FontUtils {

public:
    static TTF_Font* loadFont(const char* fileName, int size);

    static SDL_Texture* drawString(TTF_Font* font, SDL_Renderer* renderer, SDL_Color color, const char *text);
};


#endif //GEOMETRICWARS_FONTUTILS_H
