#pragma once
#include "Game.h"

class GameObj {

public:
	GameObj(const char* textures, SDL_Renderer* renderer, int x, int y);
	~GameObj();

	void update();
	void render();

private:
	
	int xPos;
	int yPos;
	
	SDL_Texture* objTexture;
	SDL_Rect srcRect, destRect;
	SDL_Renderer* renderer;
	


};
