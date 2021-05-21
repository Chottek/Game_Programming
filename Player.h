//
// Created by chotek on 21.05.2021.
//

#ifndef GEOMETRICWARS_PLAYER_H
#define GEOMETRICWARS_PLAYER_H

#include "Game.h"


class Player {
public:
    Player(const char* textures, SDL_Renderer* renderer, int x, int y, int spd);
    ~Player();

    void update();
    void render();

    void addY(int value);
    void addX(int value);

    int getSpeed();

private:
    void update_direction();

    int xPos;
    int yPos;
    int speed;

    SDL_Texture* objTexture;
    SDL_Rect srcRect, destRect;
    SDL_Renderer* renderer;

};


#endif //GEOMETRICWARS_PLAYER_H
