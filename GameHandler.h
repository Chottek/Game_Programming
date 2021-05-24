#pragma once

#ifndef GEOMETRICWARS_GAMEHANDLER_H
#define GEOMETRICWARS_GAMEHANDLER_H

#include "Game.h"

class GameHandler {

public:
    GameHandler(Game *g);
    ~GameHandler();

private:
    Game * game;
public:
    Game *getGame() const;
};


#endif //GEOMETRICWARS_GAMEHANDLER_H
