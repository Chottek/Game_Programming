#ifndef GEOMETRICWARS_PLAYER_H
#define GEOMETRICWARS_PLAYER_H

#include <list>
#include "../Game.h"
#include "Bullet.h"

class Player {
public:
    Player(SDL_Renderer* renderer, float xPos, float yPos, float spd);
    ~Player();

    void update();
    void render();

    float getX();
    float getY();

    float getCameraOffsetX() const;

    float getCameraOffsetY() const;

    void setCameraOffsets(float offX, float offY);

    std::list<Bullet*> bullets;

    int getWidth() const;

    int getHeight() const;

    void setPushBack(int bulletPower, double angl);

    void pushBack();

    const SDL_Rect &getBounds() const;

    void setBounds(const SDL_Rect &bounds);

    void addLife(int l){
        Player::life += l;
    }

    void subLife(int l){
        Player::life -= l;

        if(Player::life < 0){
            Player::life = 0;
        }
    }

    void addScore(int s){
        Player::score += s;
    }

private:
    void move();

    void getInput();

    void fire();

    bool fwd, back, left, right, shooting;
    bool isAlive;
    int life;
    int score;
    int currentBulletType;

    float x;
    float y;
    float speed;
    double angle, pushbackAngle;
    float pushback;

    double lastAngle;

    int defaultShootCoolDown;
    int shootCoolDown;

    bool isMovingForward;
    float brakingPower, speedingPower;

    float cameraOffsetX, cameraOffsetY;

    int width, height;

    SDL_Texture* objTexture;
    SDL_Rect bounds;
    SDL_Renderer* renderer;

};

#endif //GEOMETRICWARS_PLAYER_H
