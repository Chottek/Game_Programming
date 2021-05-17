
#include "Game.hpp"

Game::Game(){


}
Game::~Game(){


}

void Game::init(const char *title, int xPos, int yPos, int width, int height, bool isFullScreen){

    const int flags = 0;

    if(isFullscreen){
        flags = SDL_WINDOW_FULLSCREEN;
    }

    if(SDL_Init(SDL_INIT_EVERYTINGH) == 0){
        std::cout << "SDL Initialized.." << std::endl;

        window = SDL_CreateWindow(title, xPos, yPos, width, height, flags);
        
        if(window){
            std::cout << "Window initialized..." << std::endl;
        }

        renderer = SDL_CreateRenderer(window, -1, 0); //-1 -> index, 0 -> renderer flags
        if(renderer){
            SDL_SetRenderDrawColor(renderer, 255, 255, 255, 255); //setting drawing color to RGB(255,255,255)
            std::cout << "Renderer initialized..." << std::endl;
        }

        isRunning = true;
    }else{
        isRunning = false;
    }
}

void Game::handleEvents(){
    SDL_Event event;
    SDL_PollEvent(&event);
    switch(event.type){
        case SDL_QUIT:
            isRunning = false; break;

        default: break;
    }
}

void Game::update(){

}

void Game::render(){
    SDL_RenderClear(renderer); 
    //adding stuff to render
    SDL_RenderPresent(renderer);


}

void Game::clean(){
    SDL_DestroyWindow(window);
    SDL_DestroyRenderer(renderer);
    SDL_Quit();
    std::cout << "Game cleaned" << std::endl;
}



