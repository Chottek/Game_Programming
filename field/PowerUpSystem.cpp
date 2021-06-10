#include <ctime>
#include <iostream>
#include "PowerUpSystem.h"

int powerUpMaxLife = 255;

PowerUpSystem::PowerUpSystem(SDL_Renderer *renderer) {
    PowerUpSystem::renderer = renderer;
}


void PowerUpSystem::update() {
    generate();

    auto it = powerUps.begin();
    while (it != powerUps.end()) {

        (*it) -> setOffsets(xOffset, yOffset);
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

int lastX, lastY;

void PowerUpSystem::generate() {
    srand(time(nullptr));

    int s = rand() % 5;

    if(s != 3){
        return;
    }

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
            break;
        }
        case 2:{//left
            x = -20;
            y = (rand() % 600 - 10);
            break;
        }
        case 3:{//right
            x = 820;
            y = (rand() % 600 - 10);
        }
    }

    if(x == lastX || y == lastY){
        return;
    }

    lastX = x;
    lastY = y;
    powerUps.push_back(new PowerUp(renderer, x, y, type));
    std::cout << "Generated powerup of type " << type << " on x: " << x << " and y: " << y << " where offsets are: " << xOffset << ", " << yOffset << std::endl;

}

float PowerUpSystem::getXOffset() const {
    return xOffset;
}

float PowerUpSystem::getYOffset() const {
    return yOffset;
}
