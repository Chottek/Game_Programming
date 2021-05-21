#ifndef GEOMETRICWARS_PLAYER_H
#define GEOMETRICWARS_PLAYER_H

#include <list>
#include "Game.h"
#include "Bullet.h"

class Player {
public:
    Player(const char* textures, SDL_Renderer* renderer, float xPos, float yPos, float spd);
    ~Player();

    void update();
    void render();

    std::list<Bullet*> bullets;

private:
    void move();

    void getInput();

    void fire();

    bool fwd, back, left, right, shooting;
    bool isAlive;

    float x;
    float y;
    float speed;
    double angle;
    double lastAngle;

    bool isMovingForward;
    float brakingPower, speedingPower;

    SDL_Texture* objTexture;
    SDL_Rect srcRect, destRect;
    SDL_Renderer* renderer;

};

#endif //GEOMETRICWARS_PLAYER_H
