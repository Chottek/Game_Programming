#include "ParticleSystem.h"
#include <ctime>

ParticleSystem::ParticleSystem(SDL_Renderer *renderer, int count) {
    ParticleSystem::renderer = renderer;
}

void ParticleSystem::generate(int count, float x, float y, double angle) {
    srand(time(nullptr));
    for(int i = 0; i < count; i++){
        int shape = rand() % 4;
        int size = rand() % 20 + 8;
        int direction = rand() % 1;


      //  particles.push_back(new Particle(renderer, x, y, shape,
       //         size, direction, double rotateSpeed, float moveSpeed, double goingAngle));
    }
}

void ParticleSystem::update() {
    for(auto p : particles){
        p->update();
    }
}

void ParticleSystem::render() {
    for(auto p: particles){
        p -> render();
    }
}
