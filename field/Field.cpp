#include <sstream>
#include "Field.h"
#include "Camera.h"
#include "Player.h"
#include "Enemy.h"
#include "../utils/FontUtils.h"
#include "../utils/TextureLoader.h"
#include "../gfx/ParticleSystem.h"
#include "PowerUpSystem.h"

TTF_Font* alien12;
Camera* camera;
Player* player;
std::list<Enemy*> enemies;
ParticleSystem* particleSystem;
//PowerUpSystem* powerUpSystem;
PowerUp* powerUp;



Field::~Field(){

}

Field::Field(SDL_Renderer* ren) {
    renderer = ren;

    player = new Player(renderer, 10.0F, 10.0F, 5.0F);
    camera = new Camera();
    particleSystem = new ParticleSystem(renderer);
    //powerUpSystem = new PowerUpSystem(renderer);

    alien12 = FontUtils::loadFont("assets/CAlien.ttf", 12);

    bcgTexture = TextureLoader::loadTexture("assets/bcg.jpeg", renderer);
    bcgRect.x = 0;
    bcgRect.y = 0;
    bcgRect.w = 800;
    bcgRect.h = 600;

    for(int i = 0; i < 3; i++){
        enemies.push_back(new Enemy(renderer, 300 + i * 100, 10, 0));
        enemies.push_back(new Enemy(renderer, 500 + i * 100, 500, 1));
        enemies.push_back(new Enemy(renderer, 10 + i * 100, 500, 2));
    }

    powerUp = new PowerUp(renderer, 200, 200, 0);
}

void Field::update() {

    if(player->isAlive1()){
        player -> update();
        camera -> center(player->getX(), player->getY(), player->getWidth(), player->getHeight());
        player -> setCameraOffsets(camera->getXOffset(), camera->getYOffset());
    }
    particleSystem -> setOffsets(camera -> getXOffset(), camera -> getYOffset());
    particleSystem -> update();
    //powerUpSystem -> setOffsets(camera -> getXOffset(), camera -> getYOffset());
    //powerUpSystem -> update();
    powerUp->setOffsets(camera -> getXOffset(), camera->getYOffset());
    powerUp->update();

    if(SDL_HasIntersection(&powerUp->getBounds(), &player->getBounds())){
        particleSystem->generate(10, powerUp->getX(), powerUp->getY(), player->getAngle(), false);
        player->addLife(50);
    }

//    auto pwit = powerUpSystem->getPowerUps().begin();
//    while(pwit != powerUpSystem->getPowerUps().end()){
//        if(SDL_HasIntersection(&player->getBounds(), &(*pwit)->getBounds())){
//            std::cout << "Player has gathered!" << std::endl;
//            pwit = (powerUpSystem->getPowerUps()).erase(pwit);
//        }else pwit++;
//    }

    auto it = enemies.begin();
    while (it != enemies.end()) {
        if(player->isAlive1()){
            (*it) -> update();
            (*it) -> setCameraOffsets(camera -> getXOffset(), camera -> getYOffset());
            (*it) -> updateAngle(player -> getX(), player->getY());
        }

        //ENEMY BULLETS ITERATOR
        auto bullit = ((*it) -> bullets.begin());
        while (bullit != ((*it) -> bullets.end())) {
            if (SDL_HasIntersection(&(*bullit)->getRect(), &player->getBounds()) == SDL_TRUE) {
                particleSystem->generate((*bullit)->getDamage(), (player) -> getX(), player -> getY(), (*bullit)->getAngle(), false);
                player -> setPushBack((*bullit)->getDamage(), (*bullit) -> getAngle());
                player -> subLife((*bullit)->getDamage());
                bullit = ((*it) -> bullets).erase(bullit);

                if(player->getLife() <= 0){
                    particleSystem->generate(50, player->getX(), player->getY(), 0, true);
                    player->setIsAlive(false);
                }
            } else bullit++;
        }

        //PLAYER BULLETS ITERATOR
        auto plbullit = (player->bullets.begin());
        while (plbullit != (player -> bullets.end())) {
            if (SDL_HasIntersection(&(*plbullit)->getRect(), &(*it)->getBounds()) == SDL_TRUE) {
                particleSystem->generate((*plbullit)->getDamage(), (*it)->getX(), (*it)->getY(), (*plbullit)->getAngle(), false);
                (*it) -> setPushBack((*plbullit) -> getDamage(), (*plbullit)->getAngle());
                (*it) -> subLife((*plbullit)->getDamage());
                plbullit = (player-> bullets).erase(plbullit);
            } else plbullit++;
        }

        if(SDL_HasIntersection(&(*it)->getBounds(), &player->getBounds())){
            particleSystem->generate(14, (*it)->getX(), (*it)->getY(), 0, true);
            particleSystem->generate((*it)->getLife() / 4, (player)->getX(), (player)->getY(), (*it)->getAngle(), false);
            player->subLife((*it)->getLife());
            player->setPushBack((*it)->getLife() / 4, (*it)->getAngle());
            it = enemies.erase(it);
        }
        else if((*it) -> getLife() <= 0){ //LIFE STATEMENT OF ENEMY
            particleSystem->generate(14, (*it)->getX(), (*it)->getY(), 0, true);
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
  //  powerUpSystem -> render();
     powerUp->render();

    std::stringstream ss;
    ss << "Enemies: " << enemies.size();
    FontUtils::drawString(alien12, renderer, {255, 0, 0}, ss.str().c_str(), 685, 585);
}

bool Field::getPlayerAlive() {
    return player->isAlive1();
}

