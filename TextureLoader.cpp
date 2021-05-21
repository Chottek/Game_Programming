#include "TextureLoader.h"

SDL_Texture* TextureLoader::loadTexture(const char* textures, SDL_Renderer* renderer){
    SDL_Surface* tempSurface = IMG_Load(textures);
    SDL_Texture* texture = SDL_CreateTextureFromSurface(renderer, tempSurface);
    SDL_FreeSurface(tempSurface);

    return texture;
}