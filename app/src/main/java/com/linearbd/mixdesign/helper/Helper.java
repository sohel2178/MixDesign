package com.linearbd.mixdesign.helper;

public class Helper {

    public static double getInterpolateValue(double x, double x1,double x2,double y1,double y2){
        double k = (y2-y1)/(x2-x1);
        return (y1+k*(x-x1));
    }
}
