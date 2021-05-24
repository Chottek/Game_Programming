
#include <sstream>
#include "Game.h"
#include "field/Player.h"
#include "utils/FontUtils.h"
#include "GameHandler.h"

Player* player;
TTF_Font* alien20;
TTF_Font* alien10;

SDL_Event Game::event;

GameHandler * handler;

Game::Game(){
    Game *g = this;
    handler = new GameHandler(reinterpret_cast<Game *>(&g));  //TODO: Figure out if that's it ASAP
}

void Game::init(const char *title, int xPos, int yPos, int width, int height, bool isFullScreen){
    std::cout << &handler << std::endl;

    const int flags = 0;

    //if(isFullScreen){
    //    flags = SDL_WINDOW_FULLSCREEN;
    // }

    if(SDL_Init(SDL_INIT_EVERYTHING) == 0){
        std::cout << "SDL Initialized.." << std::endl;

        if(TTF_Init() == 0){
            std::cout << "TTF Fonts library initialized..." << std::endl;
        }//initialising TTF Font library

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

    player = new Player(renderer, 10.0F, 10.0F, 5.0F);
    alien20 = FontUtils::loadFont("assets/CAlien.ttf", 20);
    alien10 = FontUtils::loadFont("assets/CAlien.ttf", 10);
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
    player -> update();
}

void Game::render(){
    SDL_RenderClear(renderer);
    std::stringstream ss;
    ss << "X: " << player -> getX() << ", Y: " << player -> getY();
    FontUtils::drawString(alien10, renderer, {255, 0, 0}, ss.str().c_str(), 10, 10);
    player -> render();

    SDL_RenderPresent(renderer);
}

void Game::clean(){
    SDL_DestroyWindow(window);
    SDL_DestroyRenderer(renderer);
    TTF_Quit();
    SDL_Quit();
    std::cout << "Game cleaned" << std::endl;
}



