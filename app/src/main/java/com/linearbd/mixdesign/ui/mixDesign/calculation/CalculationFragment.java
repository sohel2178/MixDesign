package com.linearbd.mixdesign.ui.mixDesign.calculation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
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

import katex.hourglass.in.mathlib.MathView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalculationFragment extends BaseFragment implements CalculationContract.View {

    private TextView tvStdDev,tvHimsWorth,tvMinStn,tvWaterCementOne,tvWaterCementTwo,tvWaterCementAdopt,
            tvWaterCAirContent,tvCementContent,tvDryBulkVolume,tvDryBulkVolumeTxt,tvCementWeight,tvWaterWeight,tvCAWeight,
            tvCementWeightTwo,tvWaterWeightTwo,tvCAWeightTwo,tvFAWeightTwo,tvFieldTitle,tvCaAbsorption,
            tvWaterOne,tvWaterTwo,tvCementField,tvWaterField,tvCAField,tvFAField;

    private MathView tvCementVolume,mvWaterVolume,mvCAVolume,mvAirVolume,mvTotalAbsVol,mvAbsVolFA,mvWeightFA,
            mvTotalFreeMoisture,mvWtFAField,mvQtyCAAbsrp,mvWtofCAinField;

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
        tvDryBulkVolumeTxt = view.findViewById(R.id.dry_bulk_volume_txt);
        tvCementWeight = view.findViewById(R.id.cement_weight);
        tvCementVolume = view.findViewById(R.id.cement_volume);
        tvWaterWeight = view.findViewById(R.id.water_weight);
        mvTotalAbsVol = view.findViewById(R.id.total_abs_vol);
        mvWaterVolume = view.findViewById(R.id.water_volume);
        tvCAWeight = view.findViewById(R.id.ca_weight);
        mvCAVolume = view.findViewById(R.id.ca_volume);
        mvAirVolume = view.findViewById(R.id.air_volume);
        mvAbsVolFA = view.findViewById(R.id.abs_vol_fa);
        mvWeightFA = view.findViewById(R.id.weight_vol_fa);

        tvCementWeightTwo = view.findViewById(R.id.cement_weight_two);
        tvWaterWeightTwo = view.findViewById(R.id.water_weight_two);
        tvCAWeightTwo = view.findViewById(R.id.ca_weight_two);
        tvFAWeightTwo = view.findViewById(R.id.fine_aggregare_two);

        tvFieldTitle = view.findViewById(R.id.field_title);
        mvTotalFreeMoisture = view.findViewById(R.id.total_free_moisture);
        mvWtFAField = view.findViewById(R.id.wt_of_fa_field);
        tvCaAbsorption = view.findViewById(R.id.ca_absorption);
        mvQtyCAAbsrp = view.findViewById(R.id.qty_of_water_absorped_by_ca);
        mvWtofCAinField = view.findViewById(R.id.wt_of_ca_in_field);

        tvWaterOne = view.findViewById(R.id.water_one);
        tvWaterTwo = view.findViewById(R.id.water_two);

        tvCementField = view.findViewById(R.id.cement_weight_field);
        tvWaterField = view.findViewById(R.id.water_weight_field);
        tvCAField = view.findViewById(R.id.ca_weight_field);
        tvFAField = view.findViewById(R.id.fine_aggregare_field);


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
        double cementWeight = waterContent/(Math.min(waterCementOne,waterCementTwo));
        double caWeight = dryBulkVolume*data.getBulk_density_ca();
        double combineVolume = ((cementWeight/3.15)+(waterContent/1.00)+(caWeight/data.getSp_gr_ca())+(airContent*1000/100));

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
            " = "+String.format("%.2f",cementWeight)+" kg/m3");
        tvDryBulkVolume.setText("From above table find the dry bulk volume of coarse aggregates per unit volume of concrete is "+dryBulkVolume);

        tvDryBulkVolumeTxt.setText("So\nWeight of CA = dry bulk volume of CA per unit volume of concrete * Dry Rodded Bulk Density of CA\n\t"+
                " = "+dryBulkVolume+" * "+data.getBulk_density_ca()+"\n\t"+
                " = "+(dryBulkVolume*data.getBulk_density_ca())+" kg/m3"
        );

        tvCementWeight.setText(String.format("%.2f",cementWeight));
        tvCementVolume.setDisplayText("$\\frac"+"{"+String.format("%.2f",cementWeight)+"}"+"{"+3.15+"}"+"\\times{10^3} = "+String.format("%.2f",(cementWeight/3.15))+"\\times{10^3}"+"$");

        tvWaterWeight.setText(String.format("%.2f",waterContent));
        mvWaterVolume.setDisplayText("$\\frac"+"{"+String.format("%.2f",waterContent)+"}"+"{"+1.00+"}"+"\\times{10^3} = "+String.format("%.2f",(waterContent/1.00))+"\\times{10^3}"+"$");

        tvCAWeight.setText(String.format("%.2f",caWeight));
        mvCAVolume.setDisplayText("$\\frac"+"{"+String.format("%.2f",caWeight)+"}"+"{"+String.format("%.2f",data.getSp_gr_ca())+"}"+"\\times{10^3} = "+String.format("%.2f",(caWeight/data.getSp_gr_ca()))+"\\times{10^3}"+"$");
        mvAirVolume.setDisplayText("$\\frac"+"{"+String.format("%.2f",airContent)+"}"+"{100}"+"\\times{10^6} = "+String.format("%.2f",(airContent*1000/100))+"\\times{10^3}"+"$");

        mvTotalAbsVol.setDisplayText("$= "+String.format("%.2f",combineVolume)+"\\times{10^3} {cm^3}$");
        mvAbsVolFA.setDisplayText("Threrefore absolute volume of FA"+"$ = "+"(1000-"+String.format("%.2f",combineVolume)+")\\times{10^3}="+String.format("%.2f",(1000-combineVolume))+"\\times{10^3}$");
        mvWeightFA.setDisplayText("So, Weight of FA "+"$ = "+String.format("%.2f",(1000-combineVolume))+"\\times"+data.getSp_gr_fa()+" = "+String.format("%.2f",(1000-combineVolume)*data.getSp_gr_fa())+" kg/{m^3}$");

        tvCementWeightTwo.setText(String.format("%.2f",cementWeight)+" Kg");
        tvWaterWeightTwo.setText(String.format("%.2f",waterContent)+" Kg");
        tvCAWeightTwo.setText(String.format("%.2f",caWeight)+" Kg");
        tvFAWeightTwo.setText(String.format("%.2f",(1000-combineVolume)*data.getSp_gr_fa())+" Kg");

        tvFieldTitle.setText("The proportions are required to be adjusted for the field conditions. FA has surface" +
                " moisture of "+data.getSurface_moisture_of_fa()+" percent");

        double fieldMoistureContent = (1000-combineVolume)*data.getSp_gr_fa()*data.getSurface_moisture_of_fa()/100;

        mvTotalFreeMoisture.setDisplayText("Total free surface moisture in FA "+"$= \\frac{"+data.getSurface_moisture_of_fa()+"}{100}\\times"
                +String.format("%.2f",(1000-combineVolume)*data.getSp_gr_fa())
                +" = "+String.format("%.2f",fieldMoistureContent)+" Kg $");

        double fieldFAWeight = (1000-combineVolume)*data.getSp_gr_fa()+fieldMoistureContent;

        mvWtFAField.setDisplayText("Weight of F.A in field condition "+"$="+String.format("%.2f",(1000-combineVolume)*data.getSp_gr_fa())+" + "
                +String.format("%.2f",fieldMoistureContent)
                +" = "+String.format("%.2f",((1000-combineVolume)*data.getSp_gr_fa()+fieldMoistureContent))+"Kg$");

        tvCaAbsorption.setText("C.A absorbs "+data.getAbsorption_capacity_of_ca()+ "% water");
        double caAbsorpWater = data.getAbsorption_capacity_of_ca()*caWeight/100;
        mvQtyCAAbsrp.setDisplayText("Quantity of water absorbed by C.A "
                +"$=\\frac {"+data.getAbsorption_capacity_of_ca()+"} {100}" +
                "\\times"+String.format("%.2f",caWeight)+
                " = "+String.format("%.2f",caAbsorpWater)+"Kg$");

        double fieldCAWeignt = caWeight-caAbsorpWater;

        mvWtofCAinField.setDisplayText("Weight of C.A in field condition"
                +"$"+String.format("%.2f",caWeight)+" - "+String.format("%.2f",caAbsorpWater)
                +" = "+String.format("%.2f",(caWeight-caAbsorpWater))+"Kg$");

        tvWaterOne.setText("With regard to water, "+String.format("%.2f",fieldMoistureContent)
                +" kg of water is contributed by F.A and "
                +String.format("%.2f",caAbsorpWater)+" kg of water is " +
                "absorbed by C.A");

        double fieldWaterContent = waterContent-(fieldMoistureContent-caAbsorpWater);

        tvWaterTwo.setText("Therefore "
                +String.format("%.2f",fieldMoistureContent)+" - "+String.format("%.2f",caAbsorpWater)
                +"= "+String.format("%.2f",(fieldMoistureContent-caAbsorpWater))+" kg of extra water is contributed by aggregates. This "
                +"quantity of water is deducted from Total water\n\t\t"
                +String.format("%.2f",waterContent)+" - "+String.format("%.2f",(fieldMoistureContent-caAbsorpWater))
                +" = "+String.format("%.2f",fieldWaterContent)+" Kg"
        );

        tvCementField.setText(String.format("%.2f",cementWeight)+" Kg");
        tvWaterField.setText(String.format("%.2f",fieldWaterContent)+" Kg");
        tvCAField.setText(String.format("%.2f",fieldCAWeignt)+" Kg");
        tvFAField.setText(String.format("%.2f",fieldFAWeight)+" Kg");
        Log.d("YYYYY",waterContent+"");
        Log.d("YYYYY",dryBulkVolume+"");
    }
}
