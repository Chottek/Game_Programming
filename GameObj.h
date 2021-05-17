#pragma once
#include "Game.h"

class GameObj {

public:
	GameObj(const char* textures, SDL_Renderer* renderer, int x, int y, int speed);
	~GameObj();

	void update();
	void render();
	
	void addY(int value);
	void addX(int value);
	
	int getSpeed();

private:
	
	int xPos;
	int yPos;
	int speed;
	
	SDL_Texture* objTexture;
	SDL_Rect srcRect, destRect;
	SDL_Renderer* renderer;
	


};
