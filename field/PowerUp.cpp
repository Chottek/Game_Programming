#include "PowerUp.h"
#include "../utils/TextureLoader.h"

PowerUp::PowerUp(SDL_Renderer *renderer, float x, float y, int type) {
    PowerUp::renderer = renderer;
    PowerUp::x = x;
    PowerUp::y = y;
    PowerUp::type = type;

    switch(type){
        case 0:{
            texture = TextureLoader::loadTexture("/assets/powerups/life.png", renderer);
            break;
        }
        case 1:{
            texture = TextureLoader::loadTexture("/assets/powerups/ammo.png", renderer);
            break;
        }
        case 2:{
            texture = TextureLoader::loadTexture("/assets/powerups/shield.png", renderer);
            break;
        }
    }

    bounds.x = x;
    bounds.y = y;
    bounds.w = 16;
    bounds.h = 16;
}

void PowerUp::update() {
    if(wasSeenByPlayer){
        life--;
    }
}

void PowerUp::render() {
    SDL_SetTextureAlphaMod(texture, life);
    SDL_RenderCopy(renderer, texture, nullptr, &bounds);
}

const SDL_Rect &PowerUp::getBounds() const {
    return bounds;
}

int PowerUp::getLife() const {
    return life;
}


