package com.linearbd.mixdesign.ui.mixDesign.info;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linearbd.mixdesign.R;
import com.linearbd.mixdesign.model.Data;
import com.linearbd.mixdesign.ui.mixDesign.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends BaseFragment implements InfoContract.View {

    private TextView tvSpGrCA,tvSpGrFa,tvFM,tvBulkDensity,tvDesignStn,tvMaxSizeCA,tvSlump,tvConcType,tvAirEntrainedType,tvExposure,
        tvAbsorptionCapacity,tvMoistureContent;

    private InfoPresenter mPresenter;


    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new InfoPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_info, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvSpGrCA = view.findViewById(R.id.sp_gr_coarse);
        tvSpGrFa = view.findViewById(R.id.sp_gr_fine);
        tvFM = view.findViewById(R.id.fineness_modulus);
        tvBulkDensity = view.findViewById(R.id.dry_rodded_bulk_density);
        tvDesignStn = view.findViewById(R.id.design_stn);
        tvMaxSizeCA = view.findViewById(R.id.max_size_of_aggregate);
        tvSlump = view.findViewById(R.id.slump);
        tvConcType = view.findViewById(R.id.concrete_type);
        tvAirEntrainedType = view.findViewById(R.id.air_entrained_type);
        tvExposure = view.findViewById(R.id.exposure_cond);
        tvAbsorptionCapacity = view.findViewById(R.id.absorption_capacity_of_ca);
        tvMoistureContent = view.findViewById(R.id.moisture_content_of_fa);


        mPresenter.initialize(getData());

    }

    @Override
    public void initialize(Data data) {
        tvSpGrCA.setText(String.valueOf(data.getSp_gr_ca()));
        tvSpGrFa.setText(String.valueOf(data.getSp_gr_fa()));
        tvFM.setText(String.valueOf(data.getFm_fa()));
        tvBulkDensity.setText(String.valueOf(data.getBulk_density_ca()));
        tvDesignStn.setText(getResources().getStringArray(R.array.stn_array)[data.getDesign_stn()]);
        tvMaxSizeCA.setText(getResources().getStringArray(R.array.max_size_array)[data.getMaz_size_ca()]);
        tvSlump.setText(getResources().getStringArray(R.array.slump_range)[data.getSlump_type()]);
        tvConcType.setText(getResources().getStringArray(R.array.concrete_type_array)[data.getConcrete_type()]);
        tvAirEntrainedType.setText(getResources().getStringArray(R.array.air_concrete_type_array)[data.getConcrete_air_type()]);
        tvExposure.setText(getResources().getStringArray(R.array.exposure_array)[data.getExposure()]);
        tvAbsorptionCapacity.setText(getResources().getStringArray(R.array.ca_absorption_array)[data.getAbsorption_capacity_of_ca()]);
        tvMoistureContent.setText(getResources().getStringArray(R.array.fa_moisture_array)[data.getSurface_moisture_of_fa()]);
    }
}
