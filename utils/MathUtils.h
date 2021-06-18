#pragma once

#ifndef GEOMETRICWARS_MATHUTILS_H
#define GEOMETRICWARS_MATHUTILS_H

#include <cstdlib>
#include <cmath>
#include <random>

#define PI 3.14159265;

class MathUtils {

public:
    static double toDegrees(double value);

    static double toRadians(int degrees){
        return 3.14159265 * degrees / 180 ;
    }

    static double fRand(double min, double max){
            double f = (double) rand() / RAND_MAX;
            return min + f * (max - min);
    }

    static bool isInteger(double number){
        return ceil(number) == floor(number);
    }

    static double convertAngleVertically(double angle){
        return PI - angle;
    }

    static double convertAngleHorizontally(double angle){
        return 2 * PI - angle;
    }

    static int randomize(int min, int max){
        std::random_device rd;
        std::mt19937 mt(rd());
        std::uniform_int_distribution<int> dist(min, max);

        return dist(mt);
    }
};


#endif //GEOMETRICWARS_MATHUTILS_H
