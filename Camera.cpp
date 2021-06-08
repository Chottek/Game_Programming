#include "Camera.h"

void Camera::center(float x, float y, int width, int height) {
    xOffset = x - 800 / 2 + width / 2;
    yOffset = y - 600 / 2 + height / 2;
}

float Camera::getXOffset() const {
    return xOffset;
}

float Camera::getYOffset() const {
    return yOffset;
}
