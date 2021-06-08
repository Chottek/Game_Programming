#ifndef GEOMETRICWARS_BULLET_H
#define GEOMETRICWARS_BULLET_H

#include "SDL_render.h"

class Bullet {

public:
    Bullet(SDL_Renderer* ren, float initialX, float initialY, double direction);
    ~Bullet();

    void update();
    void render();

    int age;

    float getX() const;

    float getY() const;

    void setOffsets(float xOff, float yOff){
        xOffset = xOff;
        yOffset = yOff;
    }

    int getDamage() const;

    double getAngle() const;
    const SDL_Rect &getRect() const;

private:
    float x, y, speed;
    float xOffset, yOffset;
    double angle;
    //Rectangle for collision checking -> if(bulletRect intersects object) do_sth();
    SDL_Rect rect;

private:
    SDL_Renderer* renderer;
    SDL_Texture* objTexture;

    int damage;

    void updateRect();

};

#endif //GEOMETRICWARS_BULLET_H
