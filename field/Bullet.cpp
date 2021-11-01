#include "Bullet.h"
#include "cmath"
#include "../utils/TextureLoader.h"
#include "../utils/MathUtils.h"


Bullet::Bullet(SDL_Renderer* ren, float initialX, float initialY, double direction, int type) {
    renderer = ren;
    x = initialX;
    y = initialY;
    angle = direction;

    switch(type){
        case 0:{
            objTexture = TextureLoader::loadTexture("assets/bullets/bullet_1.png", renderer);
            speed = 8;
            damage = 5;
            break;
        }
        case 1:{
            objTexture = TextureLoader::loadTexture("assets/bullets/bullet_2.png", renderer);
            speed = 10;
            damage = 7;
            break;
        }
        case 2:{
            objTexture = TextureLoader::loadTexture("assets/bullets/bullet_3.png", renderer);
            speed = 14;
            damage = 2;
            break;
        }
        case 3:{
            objTexture = TextureLoader::loadTexture("assets/bullets/bullet_4.png", renderer);
            speed = 10;
            damage = 10;
            break;
        }
    }

    rect.x = (int) x;
    rect.y = (int) y;
    rect.w = 10;
    rect.h = 5;

    age = 0;
}

void Bullet::updateRect() {
    rect.x = (x - xOffset);
    rect.y = (y - yOffset);
}

void Bullet::update() {
    x += speed * cos(angle);
    y += speed * sin(angle);

    updateRect();
    age++;
}

void Bullet::render() {
    SDL_RenderCopyEx(renderer, objTexture, nullptr, &rect, MathUtils::toDegrees(angle), nullptr, SDL_FLIP_NONE);
}

float Bullet::getX() const {
    return x;
}

float Bullet::getY() const {
    return y;
}

double Bullet::getAngle() const {
    return angle;
}

const SDL_Rect &Bullet::getRect() const {
    return rect;
}

int Bullet::getDamage() const {
    return damage;
}

float Bullet::getXOffset() const {
    return xOffset;
}

float Bullet::getYOffset() const {
    return yOffset;
}
