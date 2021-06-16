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

    bounds.x = (int) x;
    bounds.y = (int) y;

    switch(type){
        case 0:{
            objTexture = TextureLoader::loadTexture("assets/enemy1.png", ren);
            life = 10;
            speed = 2.8F;
            defaultShootCoolDown = 50;
            bounty = life;
            bounds.w = 30;
            bounds.h = 27;
            bulletType = 0;
            break;
        }
        case 1:{
            objTexture = TextureLoader::loadTexture("assets/enemy2.png", ren);
            life = 30;
            speed = 2.0F;
            defaultShootCoolDown = 70;
            bounty = life * 2;
            bounds.w = 32;
            bounds.h = 32;
            bulletType = 1;
            break;
        }
        case 2:{
            objTexture = TextureLoader::loadTexture("assets/enemy3.png", ren);
            life = 40;
            speed = 2.5F;
            defaultShootCoolDown = 50;
            bounty = life * 3;
            bounds.w = 30;
            bounds.h = 30;
            bulletType = 3;
            break;
        }
    }


    //TODO: Change object texture depending on enemy type

    angle = 0.1;
}

bool Enemy::init() {
    switch (type) {
        case 0: {
            life = 10;
            speed = 2.8F;
            defaultShootCoolDown = 50;
            bounty = life;
            bounds.w = 30;
            bounds.h = 27;
            bulletType = 0;
            break;
        }
        case 1: {
            life = 30;
            speed = 2.0F;
            defaultShootCoolDown = 70;
            bounty = life * 2;
            bounds.w = 32;
            bounds.h = 32;
            bulletType = 1;
            break;
        }
        case 2: {
            life = 40;
            speed = 2.5F;
            defaultShootCoolDown = 50;
            bounty = life * 3;
            bounds.w = 30;
            bounds.h = 30;
            bulletType = 3;
            break;
        }
    }

    angle = 0.1;

    return true;
}

void Enemy::update() {
    updatePosition();
    pushBack();
    fire();
    move();

    auto it = bullets.begin();
    while (it != bullets.end()) {
        (*it) -> setOffsets(xOffset, yOffset);
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
    x += speed * cos(angle);
    y += speed * sin(angle);
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
    angle = atan2(y - (playerY + 6), x - (playerX + 6)) - PI;
}

void Enemy::fire() {
    srand(time(nullptr));
    int shoot = rand() % 4;

    //std::cout << this << ":" << shoot << std::endl;

    if(shoot == 0){
        if(shootCoolDown >= defaultShootCoolDown){
            bullets.push_back(new Bullet(renderer, (x + bounds.w / 2), (y + bounds.h / 2), angle, bulletType));
            shootCoolDown = 0;
        }
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

void Enemy::setPushBack(int bulletPower, double angl) {
    pushback = bulletPower;
    pushbackAngle = angl;
}

void Enemy::pushBack() {
    if(pushback > 0){
        x += pushback * cos(pushbackAngle);
        y += pushback * sin(pushbackAngle);

        pushback -= 0.5;
    }
}

double Enemy::getAngle() const {
    return angle;
}





