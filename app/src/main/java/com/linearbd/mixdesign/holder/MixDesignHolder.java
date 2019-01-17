package com.linearbd.mixdesign.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.ramotion.foldingcell.FoldingCell;

public class MixDesignHolder extends RecyclerView.ViewHolder {

    TextView tvTitle,tvCement,tvCA,tvFA,tvWater;
    FoldingCell mFoldingCell;
    private Context context;

    public MixDesignHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext();

        tvTitle = itemView.findViewById(R.id.title);
        mFoldingCell = itemView.findViewById(R.id.folding_cell);

        tvCement = itemView.findViewById(R.id.cement_weight);
        tvCA = itemView.findViewById(R.id.ca_weight);
        tvFA = itemView.findViewById(R.id.fa_weight);
        tvWater = itemView.findViewById(R.id.water_weight);

    }

    public void bind(Data data){
        tvTitle.setText(data.getTitle());
        double[] result = getDataArray(data);

        tvCement.setText(String.format("%.2f",result[0]));
        tvCA.setText(String.format("%.2f",result[1]));
        tvFA.setText(String.format("%.2f",result[2]));
        tvWater.setText(String.format("%.2f",result[3]));
    }

    public View getItemView(){
        return this.itemView;
    }

    public FoldingCell getmFoldingCell() {
        return mFoldingCell;
    }


    private double[] getDataArray(Data data){
        double[] retArr = new double[4];

        String designStn = getDesignStn(data);
        double std_deviation = StandardDeviation.getStandardDeviation(ConcreteGrade.GRADE_ARRAY[data.getDesign_stn()]);
        double k = HimsWorthConstant.getValue(5);
        double dStn = Double.parseDouble(designStn.split(" ")[0]);
        double avgStn = dStn+k*std_deviation;
        double waterCementOne = WaterCementFromCompressiveStrength.getWaterCementRatio(avgStn,data.getConcrete_air_type());
        double waterCementTwo = Exposure.getWaterCementRatio(data.getExposure(),data.getConcrete_type());
        String airType = context.getResources().getStringArray(R.array.air_concrete_type_array)[data.getConcrete_air_type()];
        double waterContent = WaterAndAirContent.getWaterContent(context.getResources().getStringArray(R.array.max_size_array)[data.getMaz_size_ca()],data.getConcrete_air_type(),context.getResources().getStringArray(R.array.slump_range)[data.getSlump_type()]);
        double airContent = WaterAndAirContent.getAirContent(context.getResources().getStringArray(R.array.max_size_array)[data.getMaz_size_ca()],data.getConcrete_air_type(),data.getExposure());
        double dryBulkVolume = DryBulkVolByUnitVol.getBulkVolPerUnitVol(AggregateSize.sizeArray[data.getMaz_size_ca()],data.getFm_fa());
        double cementWeight = waterContent/(Math.min(waterCementOne,waterCementTwo));
        double caWeight = dryBulkVolume*data.getBulk_density_ca();
        double combineVolume = ((cementWeight/3.15)+(waterContent/1.00)+(caWeight/data.getSp_gr_ca())+(airContent*1000/100));

        double fieldMoistureContent = (1000-combineVolume)*data.getSp_gr_fa()*data.getSurface_moisture_of_fa()/100;
        double fieldFAWeight = (1000-combineVolume)*data.getSp_gr_fa()+fieldMoistureContent;
        double caAbsorpWater = data.getAbsorption_capacity_of_ca()*caWeight/100;
        double fieldCAWeignt = caWeight-caAbsorpWater;
        double fieldWaterContent = waterContent-(fieldMoistureContent-caAbsorpWater);

        retArr[0]=cementWeight;
        retArr[1]=fieldCAWeignt;
        retArr[2]=fieldFAWeight;
        retArr[3]=fieldWaterContent;

        return retArr;
    }


    private String getDesignStn(Data data){
        int pos = data.getDesign_stn();
        return context.getResources().getStringArray(R.array.stn_array)[pos];
    }
}
