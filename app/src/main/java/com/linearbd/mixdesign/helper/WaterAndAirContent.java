package com.linearbd.mixdesign.helper;

public class WaterAndAirContent {
    private static final String LOW = "30-50 mm";
    private static final String MEDIUM = "80-100 mm";
    private static final String HIGH = "150-180 mm";

    public static final String[] SLUMP_ARRAY={LOW,MEDIUM,HIGH};

    private static final int NON_AIR_ENTRAINED=0;
    private static final int AIR_ENTRAINED=1;

    public static double getWaterContent(String maxAggregateSize,int airState,String slumpValue){

        switch (airState){
            case NON_AIR_ENTRAINED:

                switch (slumpValue){
                    case LOW:
                        switch (maxAggregateSize){
                            case AggregateSize.S_10:
                                return 205;

                            case AggregateSize.S_12_5:
                                return 200;

                            case AggregateSize.S_20:
                                return 185;

                            case AggregateSize.S_25:
                                return 180;

                            case AggregateSize.S_40:
                                return 160;

                            case AggregateSize.S_50:
                                return 155;

                            case AggregateSize.S_70:
                                return 145;

                            case AggregateSize.S_150:
                                return 125;
                        }
                        break;

                    case MEDIUM:
                        switch (maxAggregateSize){
                            case AggregateSize.S_10:
                                return 225;

                            case AggregateSize.S_12_5:
                                return 215;

                            case AggregateSize.S_20:
                                return 200;

                            case AggregateSize.S_25:
                                return 195;

                            case AggregateSize.S_40:
                                return 175;

                            case AggregateSize.S_50:
                                return 170;

                            case AggregateSize.S_70:
                                return 160;

                            case AggregateSize.S_150:
                                return 140;
                        }
                        break;

                    case HIGH:
                        switch (maxAggregateSize){
                            case AggregateSize.S_10:
                                return 240;

                            case AggregateSize.S_12_5:
                                return 230;

                            case AggregateSize.S_20:
                                return 210;

                            case AggregateSize.S_25:
                                return 205;

                            case AggregateSize.S_40:
                                return 185;

                            case AggregateSize.S_50:
                                return 180;

                            case AggregateSize.S_70:
                                return 170;

                            case AggregateSize.S_150:
                                return 125;
                        }
                        break;
                }

                break;

            case AIR_ENTRAINED:
                switch (slumpValue){
                    case LOW:
                        switch (maxAggregateSize){
                            case AggregateSize.S_10:
                                return 180;

                            case AggregateSize.S_12_5:
                                return 175;

                            case AggregateSize.S_20:
                                return 165;

                            case AggregateSize.S_25:
                                return 160;

                            case AggregateSize.S_40:
                                return 145;

                            case AggregateSize.S_50:
                                return 140;

                            case AggregateSize.S_70:
                                return 135;

                            case AggregateSize.S_150:
                                return 120;
                        }
                        break;

                    case MEDIUM:
                        switch (maxAggregateSize){
                            case AggregateSize.S_10:
                                return 200;

                            case AggregateSize.S_12_5:
                                return 190;

                            case AggregateSize.S_20:
                                return 180;

                            case AggregateSize.S_25:
                                return 175;

                            case AggregateSize.S_40:
                                return 160;

                            case AggregateSize.S_50:
                                return 155;

                            case AggregateSize.S_70:
                                return 150;

                            case AggregateSize.S_150:
                                return 135;
                        }
                        break;

                    case HIGH:
                        switch (maxAggregateSize){
                            case AggregateSize.S_10:
                                return 215;

                            case AggregateSize.S_12_5:
                                return 205;

                            case AggregateSize.S_20:
                                return 190;

                            case AggregateSize.S_25:
                                return 185;

                            case AggregateSize.S_40:
                                return 170;

                            case AggregateSize.S_50:
                                return 165;

                            case AggregateSize.S_70:
                                return 160;

                            case AggregateSize.S_150:
                                return 125;
                        }
                        break;
                }
                break;

        }

        return 0;
    }


}
