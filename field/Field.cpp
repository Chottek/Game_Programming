#include <sstream>
#include "Field.h"
#include "../Camera.h"
#include "../utils/FontUtils.h"
#include "../utils/TextureLoader.h"
#include "Enemy.h"

TTF_Font* alien10;
Camera* camera;
Player* player;
std::list<Enemy*> enemies;

Field::Field(SDL_Renderer* ren) {
    renderer = ren;

    player = new Player(renderer, 10.0F, 10.0F, 5.0F);
    camera = new Camera();

    alien10 = FontUtils::loadFont("assets/CAlien.ttf", 10);

    bcgTexture = TextureLoader::loadTexture("assets/bcg.jpeg", renderer);
    bcgRect.x = 0;
    bcgRect.y = 0;
    bcgRect.w = 800;
    bcgRect.h = 600;

    enemies.push_back(new Enemy(renderer, 10, 10, 0));
}

void Field::update() {
    player -> update();
    camera -> center(player->getX(), player->getY(), player->getWidth(), player->getHeight());
    player -> setCameraOffsets(camera->getXOffset(), camera->getYOffset());

    for(auto e: enemies){
        e -> update();
        e -> setCameraOffsets(camera->getXOffset(), camera->getYOffset());
        e -> updateAngle(player-> getX(), player-> getY());
    }
//    auto it = enemies.begin();
//    while (it != enemies.end()) {
//        (*it) -> update();
//        (*it) -> setCameraOffsets(camera -> getXOffset(), camera -> getYOffset());
//    } //IT CRASHES GAME, DUNNO WHY :/

}

void Field::render() {
    SDL_RenderCopy(renderer, bcgTexture, nullptr, &bcgRect); //drawing background

    player->render();

    for(auto e : enemies){ e->render(); }
}

