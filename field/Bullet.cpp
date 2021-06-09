#include "Bullet.h"
#include "cmath"
#include "../utils/TextureLoader.h"
#include "../utils/MathUtils.h"

SDL_Point center;

Bullet::Bullet(SDL_Renderer* ren, float initialX, float initialY, double direction) {
    renderer = ren;
    objTexture = TextureLoader::loadTexture("assets/bullet.png", renderer);
    x = initialX;
    y = initialY;
    angle = direction;
    speed = 10;
    damage = 7;

    rect.x = (int) x;
    rect.y = (int) y;
    rect.w = 10;
    rect.h = 5;

//    center.x = rect.x + 32;
//    center.y = rect.y + 32;

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
    //const SDL_Point point = {(int) x + 4, (int) y + 4}; -> &point
    //TODO: Figure a point to transform bullet properly around ship
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
