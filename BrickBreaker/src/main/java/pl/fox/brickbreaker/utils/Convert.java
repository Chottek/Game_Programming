package pl.fox.brickbreaker.utils;

public class Convert {

    public static double angleVert(double angle) {
        return angle = Math.PI - angle;
    }

    public static double angleHor(double angle) {
       return angle = 2 * Math.PI - angle;
    }


}
