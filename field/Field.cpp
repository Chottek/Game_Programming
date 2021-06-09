#include <sstream>
#include "Field.h"
#include "Camera.h"
#include "Player.h"
#include "Enemy.h"
#include "../utils/FontUtils.h"
#include "../utils/TextureLoader.h"
#include "../gfx/ParticleSystem.h"

TTF_Font* alien10;
Camera* camera;
Player* player;
std::list<Enemy*> enemies;
ParticleSystem* particleSystem;

Field::~Field(){

}

Field::Field(SDL_Renderer* ren) {
    renderer = ren;

    player = new Player(renderer, 10.0F, 10.0F, 5.0F);
    camera = new Camera();
    particleSystem = new ParticleSystem(renderer);

    alien10 = FontUtils::loadFont("assets/CAlien.ttf", 10);

    bcgTexture = TextureLoader::loadTexture("assets/bcg.jpeg", renderer);
    bcgRect.x = 0;
    bcgRect.y = 0;
    bcgRect.w = 800;
    bcgRect.h = 600;

    enemies.push_back(new Enemy(renderer, 10, 10, 0));
    enemies.push_back(new Enemy(renderer, 500, 500, 1));
    enemies.push_back(new Enemy(renderer, 10, 500, 2));
}

void Field::update() {
    player -> update();
    camera -> center(player->getX(), player->getY(), player->getWidth(), player->getHeight());
    player -> setCameraOffsets(camera->getXOffset(), camera->getYOffset());
    particleSystem -> update();
    particleSystem -> setOffsets(camera -> getXOffset(), camera -> getYOffset());

    auto it = enemies.begin();
    while (it != enemies.end()) {
        (*it) -> update();
        (*it) -> setCameraOffsets(camera -> getXOffset(), camera -> getYOffset());
        (*it) -> updateAngle(player -> getX(), player->getY());

        //ENEMY BULLETS ITERATOR
        auto bullit = ((*it) -> bullets.begin());
        while (bullit != ((*it) -> bullets.end())) {
            if (SDL_HasIntersection(&(*bullit)->getRect(), &player->getBounds()) == SDL_TRUE) {
                player -> setPushBack((*bullit)->getDamage(), (*bullit) -> getAngle());
                player -> subLife((*bullit)->getDamage());
                bullit = ((*it) -> bullets).erase(bullit);
            } else bullit++;
        }

        //PLAYER BULLETS ITERATOR
        auto plbullit = (player->bullets.begin());
        while (plbullit != (player -> bullets.end())) {
            if (SDL_HasIntersection(&(*plbullit)->getRect(), &(*it)->getBounds()) == SDL_TRUE) {
                particleSystem->generate(15, (*it)->getX(), (*it)->getY(), (*plbullit)->getAngle());
                (*it) -> setPushBack((*plbullit) -> getDamage(), (*plbullit)->getAngle());
                (*it) -> subLife((*plbullit)->getDamage());
                plbullit = (player-> bullets).erase(plbullit);
            } else plbullit++;
        }

        //LIFE STATEMENT OF ENEMY
        if((*it) -> getLife() <= 0){
            player->addScore((*it) -> getBounty());
            it = enemies.erase(it);
        }else it++;
    }

}

void Field::render() {
    SDL_RenderCopy(renderer, bcgTexture, nullptr, &bcgRect); //drawing background

    player->render();
    for (auto const& e : enemies) { e -> render(); }
    particleSystem -> render();
}

