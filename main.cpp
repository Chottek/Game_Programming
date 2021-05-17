#include "Game.hpp"

const char *title = "Geometric Wars";
const int width = 800;
const int height = 600;
const int position = SDL_WINDOWPOS_CENTERED;
const bool isFullScreen = false;

int main(int argc, const char * argv[]){

    Game * game = new Game();
    
    game -> init(title, position, position, width, height, isFullScreen);

    while(game -> running()){
        game -> handleEvents();
        game -> update();
        game -> render();
    }

    game -> clean();

    return 0;
}
