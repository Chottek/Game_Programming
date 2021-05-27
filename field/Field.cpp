#include <sstream>
#include "Field.h"
#include "../utils/FontUtils.h"
#include "../utils/TextureLoader.h"

TTF_Font* alien10;
Player* player;

Field::Field(SDL_Renderer* ren) {
    renderer = ren;
    player = new Player(renderer, 10.0F, 10.0F, 5.0F);
    alien10 = FontUtils::loadFont("assets/CAlien.ttf", 10);
    bcgTexture = TextureLoader::loadTexture("assets/bcg.jpeg", renderer);
    bcgRect.x = 0;
    bcgRect.y = 0;
    bcgRect.w = 800;
    bcgRect.h = 600;
}

void Field::update() {

    player->update();
}

void Field::render() {
    SDL_RenderCopy(renderer, bcgTexture, nullptr, &bcgRect); //drawing background

    player->render();
}