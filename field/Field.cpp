#include <sstream>
#include "Field.h"
#include "Camera.h"
#include "Player.h"
#include "Enemy.h"
#include "../utils/FontUtils.h"
#include "../utils/TextureLoader.h"


TTF_Font* alien10;
Camera* camera;
Player* player;
std::list<Enemy*> enemies;

Field::~Field(){

}

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

    auto it = enemies.begin();
    while (it != enemies.end()) {
        (*it) -> update();
        (*it) -> setCameraOffsets(camera -> getXOffset(), camera -> getYOffset());
        (*it) -> updateAngle(player -> getX(), player->getY());

        auto bullit = ((*it) -> bullets.begin());
        while (bullit != ((*it) -> bullets.end())) {
            (*bullit) -> update();

            if (SDL_HasIntersection(&(*bullit)->getRect(), &player->getBounds()) == SDL_TRUE) {
                player->subLife((*bullit)->getDamage());
                bullit = ((*it) -> bullets).erase(bullit);
            } else bullit++;
        }
        it++;
    }

}

void Field::render() {
    SDL_RenderCopy(renderer, bcgTexture, nullptr, &bcgRect); //drawing background

    player->render();

    for (auto const& e : enemies) { e -> render(); }
}

