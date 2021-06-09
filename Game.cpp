#include <sstream>
#include "Game.h"
#include "field/Field.h"

Field* field;
SDL_Event Game::event;

bool wasPaused = false;
int pauseClickCounter = 0;

void Game::init(const char *title, int xPos, int yPos, int width, int height, bool isFullScreen){
    const int flags = 0;

    if(SDL_Init(SDL_INIT_EVERYTHING) == 0){
        std::cout << "SDL Initialized..." << std::endl;

        if(TTF_Init() == 0){std::cout << "TTF Fonts library initialized..." << std::endl; }  //Initializing TTF font library

        SDL_SetHint( SDL_HINT_RENDER_SCALE_QUALITY, "1"); //set "antialiasing" on -> smoother edges

        window = SDL_CreateWindow(title, xPos, yPos, width, height, flags);

        if(window){
            std::cout << "Window initialized..." << std::endl;
        }

        renderer = SDL_CreateRenderer(window, -1, 0); //-1 -> index, 0 -> renderer flags
        if(renderer){
            SDL_SetRenderDrawColor(renderer, 0, 0, 0, 255); //setting drawing color to RGB(255,255,255)
            std::cout << "Renderer initialized..." << std::endl;
        }
        isRunning = true;
    }

    field = new Field(renderer);
}

void Game::handleEvents(){

    SDL_PollEvent(&event);
    switch(event.type){
        case SDL_QUIT:
            isRunning = false; break;

        default: break;
    }
}

void Game::update(){
    handleInput();

    if(!isPaused){
        field -> update();
    }
}

void Game::render(){
    SDL_RenderClear(renderer);

    field -> render();

    SDL_RenderPresent(renderer);
}

void Game::clean(){
    SDL_DestroyWindow(window);
    SDL_DestroyRenderer(renderer);
    TTF_Quit();
    SDL_Quit();
    delete field;
    std::cout << "Game cleaned" << std::endl;
}

void Game::handleInput() {
    const Uint8* key_state = SDL_GetKeyboardState(nullptr);

    if(!wasPaused){
        if(key_state[SDL_SCANCODE_ESCAPE]){
            isPaused = !isPaused;
            wasPaused = true;
            pauseClickCounter = 0;
        }
    }

    pauseClickCounter++;

    if(pauseClickCounter > 10){
        pauseClickCounter = 0;
        wasPaused = false;
    }
}



