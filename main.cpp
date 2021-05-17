#include "Game.h"

const char *title = "Geometric Wars";
const int width = 800;
const int height = 600;
const int position = SDL_WINDOWPOS_CENTERED;
const bool isFullScreen = false;
const int FPS = 60;
const int frameDelay = 1000 / FPS;

int main(int argc, const char * argv[]){

    Uint32 frameStart;
    int frameTime;

    Game * game = new Game();
    
    game -> init(title, position, position, width, height, isFullScreen);

    while(game -> running()){
    	
    	frameStart = SDL_GetTicks(); //Getting ticks from SDL instead of System nanotime
    	
   
        game -> handleEvents();
        game -> update();
        game -> render();
        
        frameTime = SDL_GetTicks() - frameStart;
        
        if(frameDelay > frameTime){
          SDL_Delay(frameDelay - frameTime); //Delay updating using SDL to make everything smoother
        }
        
    }

    game -> clean();

    return 0;
}
