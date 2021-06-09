#ifndef GEOMETRICWARS_PARTICLESYSTEM_H
#define GEOMETRICWARS_PARTICLESYSTEM_H

#include <list>
#include "Particle.h"

class ParticleSystem {

public:
    ParticleSystem(SDL_Renderer* renderer);
    ~ParticleSystem();

    void update();
    void render();

    void generate(int count, float x, float y, double angle, bool destroy);

    void setOffsets(float xOff, float yOff){
        xOffset = xOff;
        yOffset = yOff;
    }

    std::list<Particle *> &getParticles();

private:
    SDL_Renderer* renderer;
    std::list<Particle*> particles;

    float xOffset, yOffset;
};


#endif //GEOMETRICWARS_PARTICLESYSTEM_H
