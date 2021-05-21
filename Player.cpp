#include "Player.h"
#include "TextureLoader.h"

Player::Player(const char* textures, SDL_Renderer* ren, float x, float y, float spd){
    renderer = ren;
    objTexture = TextureLoader::loadTexture(textures, ren);

    xPos = x;
    yPos = y;

    speed = spd;

    angle = 0.1;

    std::cout << "Player inited with texture " << textures << std::endl;
}

void Player::update(){
    update_direction();

    srcRect.w = 64;
    srcRect.h = 64;
    srcRect.x = 0;
    srcRect.y = 0;

    destRect.x = (int) xPos;
    destRect.y = (int) yPos;
    destRect.w = srcRect.w * 2;
    destRect.h = srcRect.h * 2;

}

void Player::render(){
    SDL_RenderCopy(renderer, objTexture, nullptr, &destRect);
}

void Player::update_direction(){

    const Uint8* key_state = SDL_GetKeyboardState(nullptr);

    if (key_state[SDL_SCANCODE_W]){
        xPos += speed * cos(angle);
        yPos += speed * sin(angle);
    }
    if (key_state[SDL_SCANCODE_S]){
        xPos += (-speed / 2) * cos(angle);
        yPos += (-speed / 2) * sin(angle);
    }
    if (key_state[SDL_SCANCODE_A]){
        angle -= 0.07;
    }
    if (key_state[SDL_SCANCODE_D]){
        angle += 0.07;
    }
}

void Player::addX(float value){
    xPos += value;
}

void Player::addY(float value){
    yPos += value;
}

float Player::getSpeed(){
    return speed;
}