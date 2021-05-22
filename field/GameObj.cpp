#include "GameObj.h"
#include "../utils/TextureLoader.h"

GameObj::GameObj(const char* textures, SDL_Renderer* ren, int x, int y, int speed){
    renderer = ren;
    objTexture = TextureLoader::loadTexture(textures, ren);

    xPos = x;
    yPos = y;

    speed = speed;

    std::cout << "GameObj inited with textures " << textures << std::endl;
}

void GameObj::update(){

    srcRect.w = 64;
    srcRect.h = 64;
    srcRect.x = 0;
    srcRect.y = 0;

    destRect.x = xPos;
    destRect.y = yPos;
    destRect.w = srcRect.w * 2;
    destRect.h = srcRect.h * 2;
}

void GameObj::render(){
    SDL_RenderCopy(renderer, objTexture, NULL, &destRect);
}

void GameObj::addX(int value){
    xPos += value;
}

void GameObj::addY(int value){
    yPos += value;
}

int GameObj::getSpeed(){
    return speed;
}