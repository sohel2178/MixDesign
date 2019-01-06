package com.linearbd.mixdesign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.linearbd.mixdesign.helper.Exposure;
import com.linearbd.mixdesign.helper.HimsWorthConstant;
import com.linearbd.mixdesign.helper.WaterAndAirContent;
import com.linearbd.mixdesign.helper.WaterCementFromCompressiveStrength;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("hhhhhhh",HimsWorthConstant.getValue(2)+"");
        Log.d("hhhhhhh",WaterCementFromCompressiveStrength.getWaterCementRatio(36.5,1)+"");
        Log.d("hhhhhhh",Exposure.getWaterCementRatio(Exposure.MODERATE,Exposure.REINFORCED_CONCRETE)+"");
        Log.d("hhhhhhh",WaterAndAirContent.getWaterContent("20 mm",0,"30-50 mm")+"");
    }
}
