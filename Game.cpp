
#include "Game.h"
#include "Player.h"

Player* player;
SDL_Event Game::event;

Game::Game(){


}
Game::~Game(){


}

void Game::init(const char *title, int xPos, int yPos, int width, int height, bool isFullScreen){

    const int flags = 0;

    //if(isFullScreen){
    //    flags = SDL_WINDOW_FULLSCREEN;
    // }

    if(SDL_Init(SDL_INIT_EVERYTHING) == 0){
        std::cout << "SDL Initialized.." << std::endl;

        SDL_SetHint( SDL_HINT_RENDER_SCALE_QUALITY, "1" ); //set "antialiasing" on -> smoother edges

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

    player = new Player("assets/player.png", renderer, 10.0F, 10.0F, 5.0F);

    //SDL_Surface* tmpSurface = IMG_Load("assets/player.png");     		//loading a surface
    //playerTexture = SDL_CreateTextureFromSurface(renderer, tmpSurface);	//creating texture from surface
    //SDL_FreeSurface(tmpSurface);						//cleaning the surface

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

    player -> render();

    SDL_RenderPresent(renderer);


}

void Game::clean(){
    SDL_DestroyWindow(window);
    SDL_DestroyRenderer(renderer);
    SDL_Quit();
    std::cout << "Game cleaned" << std::endl;
}



