//
// Created by chotek on 21.05.2021.
//

#ifndef GEOMETRICWARS_PLAYER_H
#define GEOMETRICWARS_PLAYER_H

#include "Game.h"


class Player {
public:
    Player(const char* textures, SDL_Renderer* renderer, float x, float y, float spd);
    ~Player();

    void update();
    void render();

    void addY(float value);
    void addX(float value);

    float getSpeed();

private:
    void move();
    void speedUp();
    void brake(Uint8* key_state);

    float xPos;
    float yPos;
    float speed;
    double angle;
    double lastAngle;

    bool isMovingForward;
    float brakingPower;
    float braking;
    float speedingPower;

    SDL_Texture* objTexture;
    SDL_Rect srcRect, destRect;
    SDL_Renderer* renderer;

};


#endif //GEOMETRICWARS_PLAYER_H
