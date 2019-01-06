package com.linearbd.mixdesign.helper;

public class StandardDeviation {


    public static double getStandardDeviation(int concreteGrade){
        switch (concreteGrade){
            case ConcreteGrade.M10:
                return 3.5;

            case ConcreteGrade.M15:
                return 3.5;

            case ConcreteGrade.M20:
                return 4.0;

            case ConcreteGrade.M25:
                return 4.0;

            case ConcreteGrade.M30:
                return 6.0;

            case ConcreteGrade.M35:
                return 6.0;

            case ConcreteGrade.M40:
                return 6.0;

            case ConcreteGrade.M45:
                return 6.0;

            case ConcreteGrade.M50:
                return 6.0;

            case ConcreteGrade.M55:
                return 6.0;

        }

        return 0;
    }


}
