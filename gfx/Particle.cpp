#include "Particle.h"
#include "../utils/TextureLoader.h"

Particle::Particle(SDL_Renderer * ren, float initialX, float initialY, int shape,
                   float initialSize, int direction, double rotateSpeed, float moveSpeed, double goingAngle) {
    renderer = ren;
    Particle::direction = direction;
    Particle::rotateSpeed = rotateSpeed;
    Particle::size = initialSize;
    Particle::moveSpeed = moveSpeed;
    Particle::goingAngle = goingAngle;

    switch (shape) {
        case 0: {
            texture = TextureLoader::loadTexture("assets/player.png", ren); //triangle
            break;
        }
        case 1: {
            texture = TextureLoader::loadTexture("assets/enemy1.png", ren); //circle
            break;
        }
        case 2: {
            texture = TextureLoader::loadTexture("assets/enemy2.png", ren); //rectangle
            break;
        }
        case 3: {
            texture = TextureLoader::loadTexture("assets/enemy3.png", ren); //star
            break;
        }

    }

    Particle::bounds.x = (int) initialX;
    Particle::bounds.y = (int) initialY;
    bounds.w = (int) size;
    bounds.h = (int) size;
}


void Particle::update() {
    rotate();
    move();
}

void Particle::render() {
    SDL_RenderCopyEx(renderer, texture, nullptr, &bounds, angle, nullptr, SDL_FLIP_NONE);
}

void Particle::rotate() {
    angle += (direction ? -rotateSpeed : rotateSpeed);
}

void Particle::move() {
    x += moveSpeed * cos(goingAngle);
    y += moveSpeed * sin(goingAngle);

    bounds.w -= 1;
    bounds.h -= 1;
}
