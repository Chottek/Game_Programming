#include <sstream>
#include "Field.h"
#include "Camera.h"
#include "Player.h"
#include "Enemy.h"
#include "../utils/FontUtils.h"
#include "../utils/TextureLoader.h"
#include "../gfx/ParticleSystem.h"
#include "../utils/MathUtils.h"
#include <chrono>

TTF_Font* alien12, * alien24;
Camera* camera;
Player* player;
std::list<Enemy*> enemies;
ParticleSystem* particleSystem;

int maximumEnemies;
int maxRandomization;
int enemyRandomization;

auto start = std::chrono::high_resolution_clock::now();

Field::~Field(){

}

Field::Field(SDL_Renderer* ren) {
    renderer = ren;

    player = new Player(renderer, 0.0F, 0.0F, 5.0F);
    camera = new Camera();
    particleSystem = new ParticleSystem(renderer);

    alien12 = FontUtils::loadFont("assets/CAlien.ttf", 12);
    alien24 = FontUtils::loadFont("assets/CAlien.ttf", 24);

    bcgTexture = TextureLoader::loadTexture("assets/bcg.jpeg", renderer);
    bcgRect.x = 0;
    bcgRect.y = 0;
    bcgRect.w = 800;
    bcgRect.h = 600;

    maximumEnemies = 10;
    //en.push_back(Enemy(renderer....);
}

bool Field::init(){

    enemies.clear();
    start = std::chrono::high_resolution_clock::now();
    maxRandomization = 100;
    enemyRandomization = 0;

    if(player->init()){
        return true;
    }
    return false;
}

void Field::update() {
    if(player->isAlive1()){
        player -> update();
        camera -> center(player->getX(), player->getY(), player->getWidth(), player->getHeight());
        player -> setCameraOffsets(camera->getXOffset(), camera->getYOffset());
        generateEnemy();
    }
    particleSystem -> setOffsets(camera -> getXOffset(), camera -> getYOffset());
    particleSystem -> update();

    auto it = enemies.begin();
    while (it != enemies.end()) {
        if(player->isAlive1()){
            (*it) -> update();
            (*it) -> setCameraOffsets(camera -> getXOffset(), camera -> getYOffset());
            (*it) -> updateAngle(player -> getX(), player->getY());
        }

     //   ENEMY COLLISIONS WITH THEMSELVES ITERATOR
        for(auto e : enemies){
            if(e->hasBeenPushed && (*it)->hasBeenPushed){
                continue;
            }

            if(SDL_HasIntersection(&e->getBounds(), &(*it)->getBounds()) && e != (*it)) {
                if(e->getX() < (*it)->getX()){
                    e->setPushBack(3, e->getAngle() - MathUtils::toRadians(90));
                    (*it)->setPushBack(3, (*it)->getAngle() + MathUtils::toRadians(90));
                }else{
                    (*it)->setPushBack(3, -(*it)->getAngle() - MathUtils::toRadians(90));
                    e->setPushBack(3, -e->getAngle() + MathUtils::toRadians(90));
                }

                e->hasBeenPushed = true;
                (*it)->hasBeenPushed = true;
            }



        }


        //ENEMY BULLETS ITERATOR
        auto bullit = ((*it) -> bullets.begin());
        while (bullit != ((*it) -> bullets.end())) {
            if (SDL_HasIntersection(&(*bullit)->getRect(), &player->getBounds()) == SDL_TRUE) {
                particleSystem->generate((*bullit)->getDamage(), (player) -> getX(), player -> getY(), (*bullit)->getAngle(), false);
                player -> setPushBack((*bullit)->getDamage(), (*bullit) -> getAngle());
                player -> subLife((*bullit)->getDamage());
                bullit = ((*it) -> bullets).erase(bullit);
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
        }else if(abs((*it)->getX() - player->getX()) > 1000 || abs((*it)->getY() - player->getY()) > 800){
            it = enemies.erase(it);
        }else it++;
    }

    if(player -> getLife() <= 0 && player->isAlive1()){
        particleSystem->generate(50, player->getX(), player->getY(), 0, true);
        player->setIsAlive(false);
    }

}

void Field::render() {
    SDL_RenderCopy(renderer, bcgTexture, nullptr, &bcgRect); //drawing background

    player->render();
    for (auto const& e : enemies) { e -> render(); }
    particleSystem -> render();

    std::stringstream ss;

    if(player->isAlive1()){
        ss << "Enemies: " << enemies.size();
        FontUtils::drawString(alien12, renderer, {255, 0, 0}, ss.str().c_str(), 685, 585);
    }else{
        //draw "GAME OVER", score
        ss << "Score: " << player->getScore();
        FontUtils::drawString(alien24, renderer, {255, 0, 0}, ss.str().c_str(), 340, 500);
    }

}

bool Field::getPlayerAlive() {
    return player->isAlive1();
}

bool changed = false;
auto changeChecker = std::chrono::high_resolution_clock::now();

void Field::generateEnemy() {
    auto now = std::chrono::high_resolution_clock::now();

    if((now.time_since_epoch() / 1000000000)  > (changeChecker.time_since_epoch() / 1000000000)){
        changed = false;
    }

    if(std::chrono::duration_cast<std::chrono::nanoseconds>(now-start).count() / 1000000000 % 20 == 0 && !changed){
        maxRandomization -= 2;
        if(enemyRandomization <= 1){
            enemyRandomization++;
        }
        changed = true;
        changeChecker = std::chrono::high_resolution_clock::now();
    }


    if(enemies.size() > maximumEnemies || MathUtils::randomize(0, maxRandomization) != 0){
        return;
    }

    int x, y;
    int drawType = MathUtils::randomize(0, enemyRandomization);
    int draw = MathUtils::randomize(1,4);
    switch(draw){
        case 1: {
             x = MathUtils::randomize(camera->getXOffset(), camera->getXOffset() + 800);
             enemies.push_back(new Enemy(renderer, x, camera->getYOffset() - 100, drawType));
            break;
        }
        case 2: {
            x = MathUtils::randomize(camera->getXOffset(), camera->getXOffset() + 800);
            enemies.push_back(new Enemy(renderer, x, camera->getYOffset() + 700, drawType));
            break;
        }
        case 3: {
            y = MathUtils::randomize(camera->getYOffset(), camera->getYOffset() + 600);
            enemies.push_back(new Enemy(renderer, camera->getXOffset() - 100, y, drawType));
            break;
        }
        case 4: {
            y = MathUtils::randomize(camera->getYOffset(), camera->getYOffset() + 600);
            enemies.push_back(new Enemy(renderer, camera->getXOffset() + 900, y, drawType));
            break;
        }
    }
}

