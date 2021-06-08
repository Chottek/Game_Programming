#include <sstream>
#include "Player.h"

#include "../utils/TextureLoader.h"
#include "../utils/MathUtils.h"
#include "../utils/FontUtils.h"

TTF_Font * font;
int bulletTicks; //Time before bullet dissapears

Player::Player(SDL_Renderer* ren, float xPos, float yPos, float spd){
    renderer = ren;
    objTexture = TextureLoader::loadTexture("assets/player.png", ren);
    bulletTicks = 100;

    width = 64;
    height = 64;

    defaultShootCoolDown = 10;
    score = 0;
    life = 100;

    x = xPos;
    y = yPos;
    speed = spd;

    angle = 0.1;

    fwd = back = left = right = false;
    isAlive = true;

    font = FontUtils::loadFont("assets/CAlien.ttf", 12);

    bounds.w = 32;
    bounds.h = 32;
}

void Player::update(){
    getInput();
    move();

    auto it = bullets.begin();
    while (it != bullets.end()) {
        (*it) -> update();

        if ((*it)->age > bulletTicks) { //ticks until bullet gets destroyed
            it = bullets.erase(it);
        } else
            it++;
    }

    bounds.x = (int) (x - cameraOffsetX);
    bounds.y = (int) (y - cameraOffsetY);

    if(shootCoolDown < defaultShootCoolDown){
        shootCoolDown++;
    }

    //TODO: add sound of shooting using SDL_Mixer
}

void Player::render(){

    { //Drawing String with life
        std::stringstream ss;
        ss << "Life: " << life << "%";
        FontUtils::drawString(font, renderer, {(Uint8) (200 - 2 * life), (Uint8) (life * 2), (Uint8) (life / 2)}, ss.str().c_str(), 700, 10);
        ss.str("");
        ss << "X: " << x << ", Y: " << y;
        FontUtils::drawString(font, renderer, {255, 0, 0}, ss.str().c_str(), 10, 10);

        ss.str("");
        ss << "Score: " << score;
        FontUtils::drawString(font, renderer, {0, 0, 200}, ss.str().c_str(), 10, 585);
    }

    SDL_RenderCopyEx(renderer, objTexture, nullptr, &bounds, MathUtils::toDegrees(angle), nullptr, SDL_FLIP_NONE);

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
        bullets.push_back(new Bullet(renderer, x - cameraOffsetX, y - cameraOffsetY, angle));
        shootCoolDown = 0;
    }
}

float Player::getX() {
    return x;
}

float Player::getY(){
    return y;
}

float Player::getCameraOffsetX() const {
    return cameraOffsetX;
}

float Player::getCameraOffsetY() const {
    return cameraOffsetY;
}

int Player::getWidth() const {
    return width;
}

int Player::getHeight() const {
    return height;
}

void Player::setCameraOffsets(float offX, float offY) {
    Player::cameraOffsetX = offX;
    Player::cameraOffsetY = offY;
}

const SDL_Rect &Player::getBounds() const {
    return bounds;
}

