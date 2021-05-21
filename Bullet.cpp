#include "Bullet.h"
#include "cmath"

Bullet::Bullet(SDL_Renderer* ren, float initialX, float initialY, double direction) {
    renderer = ren;

    x = initialX;
    y = initialY;
    angle = direction;
    speed = 10;

    rect.x = (int) x;
    rect.y = (int) y;
    rect.w = 10;
    rect.h = 5;

    std::cout << "New bullet fired" << std::endl;

    age = 0;
}

void Bullet::updateRect() {
    rect.x = (int) x;
    rect.y = (int) y;
}

void Bullet::update() {
    x += speed * cos(angle);
    y += speed * sin(angle);

    updateRect();
    age++;
}

void Bullet::render() {
    SDL_SetRenderDrawColor(renderer,255, 255, 255, 255);
    //@TODO: Set a texture to bullet, so it's transformable within given angle
    SDL_RenderDrawRect(renderer, &rect);
    SDL_SetRenderDrawColor(renderer, 0, 0, 0, 255);
}
