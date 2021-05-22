#ifndef GEOMETRICWARS_BULLET_H
#define GEOMETRICWARS_BULLET_H

#include "../Game.h"

class Bullet {

public:
    Bullet(SDL_Renderer* ren, float initialX, float initialY, double direction);
    ~Bullet();

    void update();
    void render();

    int age;

private:
    float x, y, speed;
    double angle;
    //Rectangle for collision checking -> if(bulletRect intersects object) do_sth();
    SDL_Rect rect;
    SDL_Renderer* renderer;
    SDL_Texture* objTexture;

    void updateRect();

};

#endif //GEOMETRICWARS_BULLET_H
