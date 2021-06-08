#ifndef GEOMETRICWARS_CAMERA_H
#define GEOMETRICWARS_CAMERA_H

class Camera {

public:

    void center(float x, float y, int width, int height);

    float getXOffset() const;
    float getYOffset() const;

private:
    float xOffset, yOffset;

};


#endif //GEOMETRICWARS_CAMERA_H
