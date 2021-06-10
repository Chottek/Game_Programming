#include <ctime>
#include "PowerUpSystem.h"

int powerUpMaxLife = 255;

PowerUpSystem::PowerUpSystem(SDL_Renderer *renderer) {
    PowerUpSystem::renderer = renderer;
}


void PowerUpSystem::update() {
    auto it = powerUps.begin();
    while (it != powerUps.end()) {

       // (*it) ->setOffsets(0, 0);
        (*it) -> update();

        if ((*it) -> getLife() <= 0) {
            it = powerUps.erase(it);
        } else
            it++;
    }
}

void PowerUpSystem::render(){
    for(auto p : powerUps){
        p -> render();
    }
}

void PowerUpSystem::generate() {
    srand(time(nullptr));

    int type = rand() % 4;
    int side = rand() % 4;

    int x, y;
    switch(side){
        case 0:{ //up
            x = (rand() % 810 - 10);
            y = -20;
            break;
        }
        case 1:{//down
            x = (rand() % 810 - 10);
            y = 620;
        }
        case 2:{//left
            x = -20;
            y = (rand() % 600 - 10);
        }
        case 3:{//right
            x = 820;
            y = (rand() % 600 - 10);
        }
    }

    powerUps.push_back(new PowerUp(renderer, x - xOffset, y - yOffset, type));
}

float PowerUpSystem::getXOffset() const {
    return xOffset;
}

float PowerUpSystem::getYOffset() const {
    return yOffset;
}
