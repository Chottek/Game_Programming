#ifndef GEOMETRICWARS_GAME_H
#define GEOMETRICWARS_GAME_H

#include "SDL2/SDL.h"
#include "SDL2/SDL_image.h"
#include <iostream>
#include <SDL2/SDL_ttf.h>

class Game {

public:
    Game(){
        std::cout << "Main Game Class initialized..." << std::endl;
    }
    ~Game();

    void init(const char* title, int xPos, int yPos, int width, int height, bool isFullScreen);

    void handleEvents();
    void update();
    void render();
    void clean();

    bool running(){ return isRunning; }

    void randomizeParticlesOnPause();

    static SDL_Event event;

private:

    void handleInput();

    bool isPaused = false;
    bool hasStarted = false;
    bool isRunning = false;
    SDL_Window *window;
    SDL_Renderer *renderer;
};

#endif //GEOMETRICWARS_GAME_H
