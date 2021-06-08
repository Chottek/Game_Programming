#ifndef GEOMETRICWARS_PARTICLE_H
#define GEOMETRICWARS_PARTICLE_H

#include <SDL_render.h>

class Particle {

public:
    Particle(SDL_Renderer* renderer, float initialX, float initialY, int shape,
             float size, int direction, double rotateSpeed, float moveSpeed, double goingAngle);
    ~Particle();

    void update();
    void render();
    void rotate();
    void move();

    int getWidth(){
        return bounds.w;
    }

    int getHeight(){
        return bounds.h;
    }

private:
    float x, y;
    float moveSpeed;
    float size;
    int direction;
    double rotateSpeed, angle, goingAngle;
    SDL_Renderer* renderer;
    SDL_Rect bounds;
    SDL_Texture* texture;

};


#endif //GEOMETRICWARS_PARTICLE_H
