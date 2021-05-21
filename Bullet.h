//
// Created by chotek on 21.05.2021.
//

#ifndef GEOMETRICWARS_BULLET_H
#define GEOMETRICWARS_BULLET_H


class Bullet {

public:
    Bullet(float initialX, float initialY, double direction);
    ~Bullet();

    void update();
    void render();


private:
    float x, y;
    double angle;
    //Rectangle for collision checking -> if(bulletRect intersects object) do_sth();

};


#endif //GEOMETRICWARS_BULLET_H
