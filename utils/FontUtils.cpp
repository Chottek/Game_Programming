#include "FontUtils.h"

TTF_Font * FontUtils::loadFont(const char *fileName, int size) {
    return TTF_OpenFont(fileName, size);
}

SDL_Texture * FontUtils::drawString(TTF_Font* font, SDL_Renderer* renderer, SDL_Color color, const char *text){
    SDL_Surface* surface = TTF_RenderText_Solid(font,text, color);
    SDL_Texture* texture = SDL_CreateTextureFromSurface(renderer, surface);
    //SDL_DestroyTexture(texture) ???
    SDL_FreeSurface(surface);

    return texture;
}
