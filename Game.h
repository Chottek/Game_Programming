#pragma once

#include "SDL2/SDL.h"
#include "SDL2/SDL_image.h"
#include <iostream>

class Game {

    public:
        Game();
        ~Game();

        void init(const char* title, int xPos, int yPos, int width, int height, bool isFullScreen);
        
        void handleEvents();
        void update();
        void render();
        void clean();

        bool running(){
            return isRunning;
        }

    private:
        int count = 0;
        bool isRunning = false;
        SDL_Window *window;
        SDL_Renderer *renderer;


};


