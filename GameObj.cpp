#include "GameObj.h"
#include "TextureLoader.h"

GameObj::GameObj(const char* textures, SDL_Renderer* ren, int x, int y){
    renderer = ren;
    objTexture = TextureLoader::loadTexture(textures, ren);
	
    xPos = x;
    yPos = y;

    std::cout << "GameObj inited with textures " << textures << std::endl;
}

void GameObj::update(){
   
   xPos++;
   yPos++;
   
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
