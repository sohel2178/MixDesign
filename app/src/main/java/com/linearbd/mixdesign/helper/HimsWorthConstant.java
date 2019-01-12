package com.linearbd.mixdesign.helper;

import android.util.Log;

public class HimsWorthConstant {

    private static double[] keys = {0.1,0.6,1.0,2.5,6.6,16.0};
    private static double[] values = {3.09,2.50,2.33,1.96,1.50,1.00};

    public static double getValue(double x){
        double value =0;
        if(getIndex(x)!=-1){
            value = values[getIndex(x)];
        }else{

            int[] indexs = getIndexArray(x);


            double x1 = keys[indexs[0]];
            double x2 = keys[indexs[1]];
            double y1 = values[indexs[0]];
            double y2 = values[indexs[1]];

            value = Helper.getInterpolateValue(x,x1,x2,y1,y2);

        }

        return value;
    }

    private static int getIndex(double x){
        int retval = -1;
        for (int i=0;i<keys.length;i++){
            if(keys[i]==x){
                retval =i;
                break;
            }
        }

        return retval;
    }

    private static int[] getIndexArray(double x){
        int[] retArr = new int[2];

        for (int i=0;i<keys.length;i++){
            if(keys[i]>x){
                retArr[1] =i;
                retArr[0]=i-1;
                break;
            }
        }

        return retArr;


    }
}
