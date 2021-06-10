#include "Particle.h"
#include "../utils/TextureLoader.h"
#include "../utils/MathUtils.h"

Particle::Particle(SDL_Renderer * ren, float initialX, float initialY, int shape,
                   float initialSize, int direction, double rotateSpeed, float moveSpeed, double goingAngle, int life) {
    renderer = ren;
    Particle::direction = direction;
    Particle::rotateSpeed = rotateSpeed;
    Particle::size = initialSize;
    Particle::moveSpeed = moveSpeed;
    Particle::goingAngle = goingAngle;
    Particle::life = life; //ticks to death od particle;

    switch (shape) {
        case 0: {
            texture = TextureLoader::loadTexture("assets/particles/tr_1.png", ren);
            break;
        }
        case 1: {
            texture = TextureLoader::loadTexture("assets/particles/tr_2.png", ren);
            break;
        }
        case 2: {
            texture = TextureLoader::loadTexture("assets/particles/tr_3.png", ren);
            break;
        }
        case 3: {
            texture = TextureLoader::loadTexture("assets/particles/tr_4.png", ren);
            break;
        }
        case 4: {
            texture = TextureLoader::loadTexture("assets/particles/rect_1.png", ren);
            break;
        }
        case 5: {
            texture = TextureLoader::loadTexture("assets/particles/rect_2.png", ren);
            break;
        }
        case 6: {
            texture = TextureLoader::loadTexture("assets/particles/rect_3.png", ren);
            break;
        }
        case 7: {
            texture = TextureLoader::loadTexture("assets/particles/rect_4.png", ren);
            break;
        }


    }

    x = initialX;
    y = initialY;
    Particle::bounds.x = (int) x;
    Particle::bounds.y = (int) y;
    bounds.w = (int) size;
    bounds.h = (int) size;
}


void Particle::update() {
    rotate();
    move();
    updateBounds();
}

void Particle::render() {
    SDL_SetTextureAlphaMod(texture, 150);
    SDL_RenderCopyEx(renderer, texture, nullptr, &bounds, angle, nullptr, SDL_FLIP_NONE);
}

void Particle::rotate() {
    angle += (direction ? -rotateSpeed : rotateSpeed);
}

void Particle::updateBounds() {
    bounds.x = (int) (x - xOffset);
    bounds.y = (int) (y - yOffset);
}

void Particle::move() {
    x += moveSpeed * cos(goingAngle);
    y += moveSpeed * sin(goingAngle);
   // std::cout << "PRTX: " << x << ", PRTY: " << y << ", OFFX: " << xOffset << ", OFFY: " << yOffset << std::endl;

    if(life <= 0){
        bounds.w -= 1;
        bounds.h -= 1;
    }

    if(moveSpeed > 0){
        moveSpeed -= 0.1;
    }

    life--;
}

float Particle::getX() const {
    return x;
}

float Particle::getY() const {
    return y;
}

void Particle::setLife(int life) {
    Particle::life = life;
}
