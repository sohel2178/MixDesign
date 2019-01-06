package com.linearbd.mixdesign.helper;

public class Exposure {
    public static final int MILD=0;
    public static final int MODERATE=1;
    public static final int SEVERE=2;
    public static final int VERY_SEVERE=3;
    public static final int EXTREME=4;

    public static final int PLAIN_CONCRETE=5;
    public static final int REINFORCED_CONCRETE=6;


    public static double getWaterCementRatio(int exposure,int concreteType){
        switch (concreteType){
            case PLAIN_CONCRETE:
                switch (exposure){
                    case MILD:
                        return 0.6;

                    case MODERATE:
                        return 0.6;

                    case SEVERE:
                        return 0.5;

                    case VERY_SEVERE:
                        return 0.45;

                    case EXTREME:
                        return 0.40;
                }

            case REINFORCED_CONCRETE:
                switch (exposure){
                    case MILD:
                        return 0.55;

                    case MODERATE:
                        return 0.50;

                    case SEVERE:
                        return 0.45;

                    case VERY_SEVERE:
                        return 0.45;

                    case EXTREME:
                        return 0.40;
                }

                default:return 0;
        }
    }





}
