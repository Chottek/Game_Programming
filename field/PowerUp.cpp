#include "PowerUp.h"
#include "../utils/TextureLoader.h"

PowerUp::PowerUp(SDL_Renderer *renderer, float x, float y, int type) {
    PowerUp::renderer = renderer;
    PowerUp::x = x;
    PowerUp::y = y;
    PowerUp::type = type;
    PowerUp::wasSeenByPlayer = false;

    switch(type){
        case 0:{
            texture = TextureLoader::loadTexture("assets/powerups/health.png", renderer);
            break;
        }
        case 1:{
            texture = TextureLoader::loadTexture("assets/powerups/shield.png", renderer);
            break;
        }
        case 2:{
            texture = TextureLoader::loadTexture("assets/particles/rect_3.png", renderer);
            break;
        }
        default:{
            PowerUp::type = 0;
            texture = TextureLoader::loadTexture("assets/powerups/health.png", renderer);
            break;
        }
    }

    bounds.x = x;
    bounds.y = y;
    bounds.w = 16;
    bounds.h = 16;
}

void PowerUp::update() {
    updateBounds();

    life -= 0.5;

    if(life <= 0){
        life = 0;
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

void PowerUp::updateBounds() {
    bounds.x = (int) (x - xOffset);
    bounds.y = (int) (y - yOffset);
}

float PowerUp::getX() const {
    return x;
}

float PowerUp::getY() const {
    return y;
}



