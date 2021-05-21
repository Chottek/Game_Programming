#include "Player.h"
#include "TextureLoader.h"

Player::Player(const char* textures, SDL_Renderer* ren, int x, int y, int spd){
    renderer = ren;
    objTexture = TextureLoader::loadTexture(textures, ren);

    xPos = x;
    yPos = y;

    speed = spd;

    std::cout << "Player inited with texture " << textures << std::endl;
}

void Player::update(){

    srcRect.w = 64;
    srcRect.h = 64;
    srcRect.x = 0;
    srcRect.y = 0;

    destRect.x = xPos;
    destRect.y = yPos;
    destRect.w = srcRect.w * 2;
    destRect.h = srcRect.h * 2;

    update_direction();

}

void Player::render(){
    SDL_RenderCopy(renderer, objTexture, nullptr, &destRect);
}

void Player::update_direction(){

    const Uint8* key_state = SDL_GetKeyboardState(nullptr);

    if (key_state[SDL_SCANCODE_W]){
        yPos -= speed;
    }
    else if (key_state[SDL_SCANCODE_S]){
        yPos += speed;
    }
    else if (key_state[SDL_SCANCODE_A]){
        xPos -= speed;
    }
    else if (key_state[SDL_SCANCODE_D]){
        xPos += speed;
    }
}

void Player::addX(int value){
    xPos += value;
}

void Player::addY(int value){
    yPos += value;
}

int Player::getSpeed(){
    return speed;
}