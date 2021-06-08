#ifndef GEOMETRICWARS_ENEMY_H
#define GEOMETRICWARS_ENEMY_H

#include <list>
#include "Bullet.h"

class Enemy {

public:
    Enemy(SDL_Renderer* ren, float x, float y, int type);
    ~Enemy();

    void update();
    void render();
    void updatePosition();
    void move();

    float getX() const;

    float getY() const;

    float getXOffset() const;

    float getYOffset() const;

    void setCameraOffsets(float x, float y);

    void fire();

    void updateAngle(float playerX, float playerY);

    std::list<Bullet*> bullets;

    const SDL_Rect &getBounds() const;

    void addLife(int l){
        Enemy::life += l;
    }

    void subLife(int l){
        Enemy::life -= l;

        if(Enemy::life < 0){
            Enemy::life = 0;
            Enemy::isDead = true;
        }
    }

    bool isDead1() const;

    int getLife() const;

    int getBounty() const;

private:
    float x, y, speed;
    double angle;
    int type;
    int life;
    int bounty; //score

    int width, height;

    bool isDead;

    int defaultShootCoolDown;
    int shootCoolDown;

    float xOffset, yOffset;

    SDL_Texture* objTexture;
    SDL_Rect bounds;
    SDL_Renderer* renderer;
};


#endif //GEOMETRICWARS_ENEMY_H
