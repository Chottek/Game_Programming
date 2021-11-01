#include "ParticleSystem.h"
#include "../utils/MathUtils.h"
#include <ctime>
#include <iostream>

ParticleSystem::ParticleSystem(SDL_Renderer *renderer) {
    ParticleSystem::renderer = renderer;
}

void ParticleSystem::generate(int count, float x, float y, double angle, bool destroy) {
    srand(time(0)); //@TODO: Change seed, so it's more random
    for(int i = 0; i < count; i++){
        int shape = rand() % 8;
        int size = destroy ? rand() % 16 + 8 : rand() % 6 + 2;
        int direction = rand() % 1;
        double rotateSpeed = MathUtils::fRand(0.1, 0.7);
        double moveSpeed = MathUtils::fRand(2.0, 6.0);
        double spreadAngle = destroy ? MathUtils::fRand(0, 6.2) : MathUtils::fRand(angle - 0.5, angle + 0.5);

        particles.push_back(new Particle(renderer, x, y, shape,
                size, direction, rotateSpeed, moveSpeed, spreadAngle, destroy ? 200 : 100));
    }
}


void ParticleSystem::update() {
    auto it = particles.begin();
    while (it != particles.end()) {
        (*it) -> update();
        (*it) -> setOffsets(xOffset, yOffset);

        if ((*it) -> getWidth() <= 0) {
            it = particles.erase(it);
        } else
            it++;
    }
}

void ParticleSystem::render() {
    for(auto p: particles){
        p -> render();
    }
}

std::list<Particle *> ParticleSystem::getParticles(){
    return particles;
}
