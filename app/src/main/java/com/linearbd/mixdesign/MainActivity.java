package com.linearbd.mixdesign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.linearbd.mixdesign.helper.AggregateSize;
import com.linearbd.mixdesign.helper.ConcreteGrade;
import com.linearbd.mixdesign.helper.DryBulkVolByUnitVol;
import com.linearbd.mixdesign.helper.Exposure;
import com.linearbd.mixdesign.helper.HimsWorthConstant;
import com.linearbd.mixdesign.helper.StandardDeviation;
import com.linearbd.mixdesign.helper.WaterAndAirContent;
import com.linearbd.mixdesign.helper.WaterCementFromCompressiveStrength;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Log.d("hhhhhhh",HimsWorthConstant.getValue(2)+"");
        Log.d("hhhhhhh",WaterCementFromCompressiveStrength.getWaterCementRatio(36.5,1)+"");
        Log.d("hhhhhhh",Exposure.getWaterCementRatio(Exposure.MODERATE,Exposure.REINFORCED_CONCRETE)+"");
        Log.d("hhhhhhh",WaterAndAirContent.getWaterContent("20 mm",0,"30-50 mm")+"");
        Log.d("hhhhhhh", StandardDeviation.getStandardDeviation(ConcreteGrade.M30)+"");
        Log.d("hhhhhhh", DryBulkVolByUnitVol.getBulkVolPerUnitVol(AggregateSize.S_20,1.8)+"");*/
    }
}
