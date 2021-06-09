#ifndef GEOMETRICWARS_PARTICLESYSTEM_H
#define GEOMETRICWARS_PARTICLESYSTEM_H

#include <list>
#include "Particle.h"

class ParticleSystem {

public:
    ParticleSystem(SDL_Renderer* renderer, int count);
    ~ParticleSystem();

    void update();
    void render();

    void generate(int count, float x, float y, double angle);

private:
    SDL_Renderer* renderer;
    std::list<Particle*> particles;

};


#endif //GEOMETRICWARS_PARTICLESYSTEM_H
