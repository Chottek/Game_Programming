#pragma once

#ifndef GEOMETRICWARS_MATHUTILS_H
#define GEOMETRICWARS_MATHUTILS_H

#include <cstdlib>
#include <cmath>

#define PI 3.14159265;

class MathUtils {

public:
    static double toDegrees(double value);

    static double fRand(double min, double max){
            double f = (double) rand() / RAND_MAX;
            return min + f * (max - min);
    }

    static bool isInteger(double number){
        return ceil(number) == floor(number);
    }
};


#endif //GEOMETRICWARS_MATHUTILS_H
