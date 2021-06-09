#include "ParticleSystem.h"
#include "../utils/MathUtils.h"
#include <ctime>

ParticleSystem::ParticleSystem(SDL_Renderer *renderer) {
    ParticleSystem::renderer = renderer;
}

void ParticleSystem::generate(int count, float x, float y, double angle) {
    srand(time(nullptr));
    for(int i = 0; i < count; i++){
        int shape = rand() % 4;
        int size = rand() % 12 + 8;
        int direction = rand() % 1;
        double rotateSpeed = MathUtils::fRand(0.1, 0.7);
        double moveSpeed = MathUtils::fRand(2.0, 6.0);

        particles.push_back(new Particle(renderer, x, y, shape,
                size, direction, rotateSpeed, moveSpeed, angle));
    }
}


void ParticleSystem::update() {
    auto it = particles.begin();
    while (it != particles.end()) {
        (*it) -> update();
        (*it) ->setOffsets(xOffset, yOffset);

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
