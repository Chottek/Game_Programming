#include "FontUtils.h"

TTF_Font * FontUtils::loadFont(const char *fileName, int size) {
    return TTF_OpenFont(fileName, size);
}

void FontUtils::drawString(TTF_Font* font, SDL_Renderer* renderer, SDL_Color color, const char *text, int x, int y){
    SDL_Surface* surface = TTF_RenderText_Solid(font,text, color);
    SDL_Texture* texture = SDL_CreateTextureFromSurface(renderer, surface);
    //SDL_DestroyTexture(texture) ???
    int texW = 0;
    int texH = 0;
    SDL_QueryTexture(texture, nullptr, nullptr, &texW, &texH);
    SDL_Rect dstrect = {x, y, texW, texH};
    SDL_FreeSurface(surface);
    SDL_RenderCopy(renderer, texture, nullptr, &dstrect);
}
