#include "Enemy.h"
#include "../utils/MathUtils.h"
#include "../utils/TextureLoader.h"

int bulletTick; //Time before bullet dissapears

Enemy::Enemy(SDL_Renderer *ren, float x, float y, int type) {
    renderer = ren;
    Enemy::x = x;
    Enemy::y = y;
    Enemy::type = type;

    bulletTick = 100;

    switch(type){
        case 0:{
            objTexture = TextureLoader::loadTexture("assets/player.png", ren);
            life = 10;
            speed = 2.0F;
            defaultShootCoolDown = 70;
            bounty = life;
            break;
        }
    }

    bounds.x = (int) x;
    bounds.y = (int) y;
    bounds.w = 32;
    bounds.h = 32;

    //TODO: Change object texture depending on enemy type

    angle = 0;

    xOffset = x;
    yOffset = y;

}

void Enemy::update() {
    updatePosition();

    fire();

    auto it = bullets.begin();
    while (it != bullets.end()) {
        (*it) -> update();

        if ((*it)->age > bulletTick) { //ticks until bullet gets destroyed
            it = bullets.erase(it);
        } else it++;
    }
}


void Enemy::render() {
    SDL_RenderCopyEx(renderer, objTexture, nullptr, &bounds, MathUtils::toDegrees(angle), nullptr, SDL_FLIP_NONE);

    for (auto const& i : bullets) { i->render(); }
}

float Enemy::getX() const {
    return x;
}

float Enemy::getY() const {
    return y;
}

void Enemy::updatePosition() {
    bounds.x = (int) (x - xOffset);
    bounds.y = (int) (y - yOffset);
}

void Enemy::move() {

}

float Enemy::getXOffset() const {
    return xOffset;
}

void Enemy::setCameraOffsets(float xOff, float yOff) {
    xOffset = xOff;
    yOffset = yOff;
}

float Enemy::getYOffset() const {
    return yOffset;
}

void Enemy::updateAngle(float playerX, float playerY) {
    angle = atan2(y - (playerY + 10), x - (playerX + 10)) - PI;
}

void Enemy::fire() {
    if(shootCoolDown >= defaultShootCoolDown){
        bullets.push_back(new Bullet(renderer, x - xOffset, y - yOffset, angle));
        shootCoolDown = 0;
    }

    shootCoolDown++;
}

const SDL_Rect &Enemy::getBounds() const {
    return bounds;
}

bool Enemy::isDead1() const {
    return isDead;
}

int Enemy::getLife() const {
    return life;
}

int Enemy::getBounty() const {
    return bounty;
}





