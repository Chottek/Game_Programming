package pl.fox.avoider.utils;

import java.util.ArrayList;

public class Convert {

    public static void angleVert(ArrayList<Double> angle, int index) {
        double arg1 = angle.get(index);
        double arg2 = Math.PI - arg1;
        angle.set(index, arg2);
    }

    public static void angleHor(ArrayList<Double> angle, int index) {
        double arg1 = angle.get(index);
        double arg2 = 2 * Math.PI - arg1;
        angle.set(index, arg2);
    }


}
