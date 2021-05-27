#include <sstream>
#include "Player.h"
#include "../utils/TextureLoader.h"
#include "../utils/MathUtils.h"
#include "../utils/FontUtils.h"

TTF_Font * font;

Player::Player(SDL_Renderer* ren, float xPos, float yPos, float spd){
    renderer = ren;
    objTexture = TextureLoader::loadTexture("assets/player.png", ren);

    defaultShootCoolDown = 10;
    life = 100;

    x = xPos;
    y = yPos;
    speed = spd;


    angle = 0.1;

    fwd = back = left = right = false;
    isAlive = true;

    font = FontUtils::loadFont("assets/CAlien.ttf", 12);
}

void Player::update(){
    getInput();
    move();

    for (auto const& i : bullets) {
         i->update();
    }

//    auto i = bullets.begin();
//    while (i != bullets.end()){
//        (*i)->update();
//        if ((*i) -> age > 10000){
//            bullets.erase(i++);  // alternatively, i = items.erase(i);
//        }
//    }

    destRect.x = (int) x;
    destRect.y = (int) y;
    destRect.w = 32;
    destRect.h = 32;

    if(shootCoolDown < defaultShootCoolDown){
        shootCoolDown++;
    }

}

void Player::render(){

    { //Drawing String with life
        std::stringstream ss;
        ss << "Life: " << life << "%";
        FontUtils::drawString(font, renderer, {0, 200, 100}, ss.str().c_str(), 700, 10);
        ss.str("");
        ss << "X: " << x << ", Y: " << y;
        FontUtils::drawString(font, renderer, {255, 0, 0}, ss.str().c_str(), 10, 10);
    }

    SDL_RenderCopyEx(renderer, objTexture, nullptr, &destRect, MathUtils::toDegrees(angle), nullptr, SDL_FLIP_NONE);

    for (auto const& i : bullets) { i->render(); }

}

void Player::getInput() {

    const Uint8* key_state = SDL_GetKeyboardState(nullptr);

    if (key_state[SDL_SCANCODE_W] || key_state[SDL_SCANCODE_UP]){
        fwd = true; back = false;
    }else{
        fwd = false;
    }
    if (key_state[SDL_SCANCODE_S] || key_state[SDL_SCANCODE_DOWN]){
        back = true; fwd = false;
    }else{
        back = false;
    }
    if (key_state[SDL_SCANCODE_A] || key_state[SDL_SCANCODE_LEFT]){
        left = true; right = false;
    }else{
        left = false;
    }
    if (key_state[SDL_SCANCODE_D] || key_state[SDL_SCANCODE_RIGHT]){
        right = true; left = false;
    }else{
        right = false;
    }
    if(key_state[SDL_SCANCODE_SPACE]){
        shooting = true;
    }else{
        shooting = false;
    }
}

void Player::move(){

    if (fwd){
        isMovingForward = true;
        x += speed * cos(angle);
        y += speed * sin(angle);
        brakingPower = speed;
    }else{
        if(isMovingForward && brakingPower == speed){
            lastAngle = angle;
        }

        if(isMovingForward && brakingPower > 0){
            x += brakingPower * cos(lastAngle);
            y += brakingPower * sin(lastAngle);
            brakingPower -= 0.1;
        }

        if(isMovingForward && brakingPower <= 0){
            isMovingForward = false;
        }
    }
    if (back){
        x += (-speed / 2) * cos(angle);
        y += (-speed / 2) * sin(angle);
    }
    if (left){
        angle -= 0.1;
    }
    if (right){
        angle += 0.1;
    }
    if(shooting){
        fire();
    }
}


void Player::fire(){
    if(shootCoolDown >= defaultShootCoolDown){
        bullets.push_back(new Bullet(renderer, x, y, angle));
        shootCoolDown = 0;
    }
}

float Player::getX() {
    return x;
}

float Player::getY(){
    return y;
}
