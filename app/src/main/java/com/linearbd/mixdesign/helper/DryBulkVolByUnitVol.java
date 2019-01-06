package com.linearbd.mixdesign.helper;

public class DryBulkVolByUnitVol {

    private static final double[][] data ={
        {0.50,0.48,0.46,0.44},
        {0.59,0.57,0.55,0.53},
        {0.66,0.64,0.62,0.60},
        {0.71,0.69,0.67,0.65},
        {0.75,0.73,0.71,0.69},
        {0.78,0.76,0.74,0.72},
        {0.82,0.80,0.78,0.76},
        {0.87,0.85,0.83,0.81}
    };


    public static double getBulkVolPerUnitVol(String aggregateSize,double finenessModulus){
        double retVal =0;
        double[] arr = getArray(aggregateSize);

        retVal = getValue(arr,finenessModulus);

       /* if(getFinenessIndex(finenessModulus)!=-1){
            retVal = arr[getFinenessIndex(finenessModulus)];
        }else {

        }*/



        return retVal;

    }

    private static double getValue(double[] arr,double finenessModulus){
        double firstX = FinenessModulus.FM_ARR[0];
        double finalX = FinenessModulus.FM_ARR[FinenessModulus.FM_ARR.length-1];

        double firstY = arr[0];
        double finalY = arr[arr.length-1];

        double slope = (finalY-firstY)/(finalX-firstX);

        return firstY+slope*(finenessModulus-firstX);
    }

    public static double[] getArray(String aggregateSize){
        return data[getIndex(aggregateSize)];
    }

    private static int getIndex(String aggregateSize){
        int retInt =-1;

        for (int i=0;i<AggregateSize.sizeArray.length;i++){
            if(AggregateSize.sizeArray[i].equals(aggregateSize)){
                retInt =i;
                break;
            }
        }

        return retInt;
    }


    private static int getFinenessIndex(double finenessModulus){
        int retIndex = -1;

        for (int i=0;i<FinenessModulus.FM_ARR.length;i++){
            if(FinenessModulus.FM_ARR[i]==finenessModulus){
                retIndex = i;
                break;
            }
        }

        return retIndex;
    }
}
