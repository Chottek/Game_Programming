#include "Player.h"
#include "TextureLoader.h"
#include "MathUtils.h"

Player::Player(const char* textures, SDL_Renderer* ren, float xPos, float yPos, float spd){
    renderer = ren;
    objTexture = TextureLoader::loadTexture(textures, ren);

    x = xPos;
    y = yPos;
    speed = spd;

    angle = 0.1;

    fwd = back = left = right = false;
    isAlive = true;
}

void Player::update(){
    getInput();
    move();

    std::cout << bullets.size() << std::endl;

    for (auto const& i : bullets) {
         i->update();
//         if(i->age > 500){
//             bullets.remove(i); //Deal with ConcurrentModificationException here
//         }
    }

//    auto i = bullets.begin();
//    while (i != bullets.end()){
//        (*i)->update();
//        if ((*i) -> age > 10000){
//            bullets.erase(i++);  // alternatively, i = items.erase(i);
//        }
//    }

    srcRect.w = 32;
    srcRect.h = 32;
    srcRect.x = 0;
    srcRect.y = 0;

    destRect.x = (int) x;
    destRect.y = (int) y;
    destRect.w = srcRect.w;
    destRect.h = srcRect.h;

}

void Player::render(){
    //SDL_RenderCopy(renderer, objTexture, nullptr, &destRect);
    SDL_RenderCopyEx(renderer, objTexture, nullptr, &destRect, MathUtils::toDegrees(angle), nullptr, SDL_FLIP_NONE);

    for (auto const& i : bullets) {
        i->render();
    }

}

void Player::fire(){
    bullets.push_back(new Bullet(renderer, x + destRect.w, y + destRect.h / 2, angle));
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
        std::cout << "PIF PAF" << std::endl;
        fire();
    }
}
