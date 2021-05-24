#include "GameHandler.h"

GameHandler::GameHandler(Game *g) {
    game = g;
}

Game *GameHandler::getGame() const {
    return game;
}
