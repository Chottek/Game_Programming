#include "Game.hpp"

Game *game = nullpntr;

int main(int argc, const char * argv[]){

    game = new Game();

    while(game->isRunning()){
        game -> handleEvents();
        game -> udpate();
        game -> render();
    }

    game -> clean();

    return 0;
}