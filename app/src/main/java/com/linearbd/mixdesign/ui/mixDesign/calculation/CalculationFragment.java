package com.linearbd.mixdesign.ui.mixDesign.calculation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linearbd.mixdesign.R;
import com.linearbd.mixdesign.helper.AggregateSize;
import com.linearbd.mixdesign.helper.ConcreteGrade;
import com.linearbd.mixdesign.helper.DryBulkVolByUnitVol;
import com.linearbd.mixdesign.helper.Exposure;
import com.linearbd.mixdesign.helper.HimsWorthConstant;
import com.linearbd.mixdesign.helper.StandardDeviation;
import com.linearbd.mixdesign.helper.WaterAndAirContent;
import com.linearbd.mixdesign.helper.WaterCementFromCompressiveStrength;
import com.linearbd.mixdesign.model.Data;
import com.linearbd.mixdesign.ui.mixDesign.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalculationFragment extends BaseFragment implements CalculationContract.View {

    private TextView tvStdDev,tvHimsWorth,tvMinStn,tvWaterCementOne,tvWaterCementTwo,tvWaterCementAdopt,
            tvWaterCAirContent,tvCementContent,tvDryBulkVolume;

    private CalculationPresenter mPresenter;


    public CalculationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new CalculationPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculation, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvStdDev = view.findViewById(R.id.std_deviation);
        tvHimsWorth = view.findViewById(R.id.himsworth);
        tvMinStn = view.findViewById(R.id.min_stn);
        tvWaterCementOne = view.findViewById(R.id.water_cement_one);
        tvWaterCementTwo = view.findViewById(R.id.water_cement_two);
        tvWaterCementAdopt = view.findViewById(R.id.water_cement_adopt);
        tvWaterCAirContent = view.findViewById(R.id.water_air_content);
        tvCementContent = view.findViewById(R.id.cement_content);
        tvDryBulkVolume = view.findViewById(R.id.dry_bulk_volume);


        mPresenter.initialize(getData());


    }

    @Override
    public void initialize(Data data) {
        String designStn = getDesignStn();
        double std_deviation = StandardDeviation.getStandardDeviation(ConcreteGrade.GRADE_ARRAY[getData().getDesign_stn()]);
        double k = HimsWorthConstant.getValue(5);
        double dStn = Double.parseDouble(designStn.split(" ")[0]);
        double avgStn = dStn+k*std_deviation;
        double waterCementOne = WaterCementFromCompressiveStrength.getWaterCementRatio(avgStn,data.getConcrete_air_type());
        double waterCementTwo = Exposure.getWaterCementRatio(data.getExposure(),data.getConcrete_type());
        String airType = getResources().getStringArray(R.array.air_concrete_type_array)[data.getConcrete_air_type()];
        double waterContent = WaterAndAirContent.getWaterContent(getResources().getStringArray(R.array.max_size_array)[data.getMaz_size_ca()],data.getConcrete_air_type(),getResources().getStringArray(R.array.slump_range)[data.getSlump_type()]);
        double airContent = WaterAndAirContent.getAirContent(getResources().getStringArray(R.array.max_size_array)[data.getMaz_size_ca()],data.getConcrete_air_type(),data.getExposure());
        double dryBulkVolume = DryBulkVolByUnitVol.getBulkVolPerUnitVol(AggregateSize.sizeArray[data.getMaz_size_ca()],data.getFm_fa());

        tvStdDev.setText("From above Table assume standard deviation for "+ designStn +" grade concrete is "+std_deviation+" MPa");
        tvHimsWorth.setText("Assume 5% of result are allowed to fall below specified design strength.\nForm above find the value of K = "+String.format("%.2f",k));
        tvMinStn.setText("Calculate Mean Strength from the following formula\n\t" +
                "Mean Strength(fm)= Min Strength(fmin) + K * std_deviation\n\t"+
                "Mean Strength(fm)= "+dStn+" + "+String.format("%.2f",k)+" * "+std_deviation+"\n\t"+
                "Mean Strength(fm)= "+String.format("%.2f",avgStn)+" MPa")
        ;

        tvWaterCementOne.setText("Form above Table Find Water-Cement ratio "+waterCementOne+". For the Avg Strength "+String.format("%.2f",avgStn)+" MPa, and "+airType+" concrete.");
        tvWaterCementTwo.setText("Form above Table Find Water-Cement ratio "+waterCementTwo+". For "+getResources().getStringArray(R.array.exposure_array)[data.getExposure()]+" Exposure, and "+getResources().getStringArray(R.array.concrete_type_array)[data.getConcrete_type()]+" concrete.");
        tvWaterCementAdopt.setText("From above two water-cement ratio "+waterCementOne+" and "+waterCementTwo+" take minimum water-cement ratio = "+Math.min(waterCementOne,waterCementTwo));
        tvWaterCAirContent.setText("From above table find water content "+waterContent+" kg/m3 and air-content "+airContent+"% for slump "+getResources().getStringArray(R.array.slump_range)[data.getSlump_type()]+" and maximum size of aggregate "+getResources().getStringArray(R.array.max_size_array)[data.getMaz_size_ca()]);
        tvCementContent.setText("So,\nRequired Cement Content = Water Content/water-cement ratio\n\t"+
            " = "+waterContent+"/"+(Math.min(waterCementOne,waterCementTwo))+"\n\t"+
            " = "+String.format("%.2f",waterContent/(Math.min(waterCementOne,waterCementTwo)))+" kg/m3");

        Log.d("YYYYY",waterContent+"");
        Log.d("YYYYY",dryBulkVolume+"");
    }
}
