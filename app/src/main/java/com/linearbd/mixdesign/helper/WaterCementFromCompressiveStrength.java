package com.linearbd.mixdesign.helper;

import android.util.Log;

import java.text.DecimalFormat;

public class WaterCementFromCompressiveStrength {

    private static double[] avgStns={15,20,25,30,35,40,45};
    private static double[] nonAir={0.80,0.70,0.62,0.55,0.48,0.43,0.38};
    private static double[] air={0.71,0.61,0.53,0.46,0.40,0,0};
    private static final int NON_AIR_ENTRAINED=0;
    private static final int AIR_ENTRAINED=1;

    private static DecimalFormat df = new DecimalFormat("#.##");


    public static double getWaterCementRatio(double avgStn,int airState){
        if(airState==AIR_ENTRAINED){
            return getAirEntrainedValue(avgStn);
        }else {
            return getNonAirEntrained(avgStn);
        }
    }

    private static double getNonAirEntrained(double avgStn) {
        double value =0;
        if(getIndex(avgStn)!=-1){
            value = nonAir[getIndex(avgStn)];
        }else{
            int[] indexs = getIndexArray(avgStn);


            double x1 = avgStns[indexs[0]];
            double x2 = avgStns[indexs[1]];
            double y1 = nonAir[indexs[0]];
            double y2 = nonAir[indexs[1]];

            value = Helper.getInterpolateValue(avgStn,x1,x2,y1,y2);
        }

        return Double.parseDouble(df.format(value));
    }

    private static double getAirEntrainedValue(double avgStn) {
        double value =0;
        if(getIndex(avgStn)!=-1){
            value = air[getIndex(avgStn)];
        }else{
            int[] indexs = getIndexArray(avgStn);


            double x1 = avgStns[indexs[0]];
            double x2 = avgStns[indexs[1]];
            double y1 = air[indexs[0]];
            double y2 = air[indexs[1]];

            value = Helper.getInterpolateValue(avgStn,x1,x2,y1,y2);
        }



        return Double.parseDouble(df.format(value));
    }


    private static int getIndex(double x){
        int retval = -1;
        for (int i=0;i<avgStns.length;i++){
            if(avgStns[i]==x){
                retval =i;
                break;
            }
        }

        return retval;
    }

    private static int[] getIndexArray(double x){
        int[] retArr = new int[2];

        for (int i=0;i<avgStns.length;i++){
            if(avgStns[i]>x){
                retArr[1] =i;
                retArr[0]=i-1;
                break;
            }
        }

        return retArr;


    }
}
