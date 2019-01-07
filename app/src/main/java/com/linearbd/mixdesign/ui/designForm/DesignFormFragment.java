package com.linearbd.mixdesign.ui.designForm;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.linearbd.mixdesign.R;
import com.linearbd.mixdesign.model.Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class DesignFormFragment extends DialogFragment implements View.OnClickListener,DesignFormContract.View {

    private TextView tvCancel,tvSave;

    private AppCompatSpinner spDesignStn,spAbsorptionCapacityofCA,spSurfaceMoistureOfFA
            ,spExposure,spConcreteType,spAirType,spSlump,spMaxSizeOfAggregate;


    private ArrayAdapter<String> stnAdapter,absorpCaAdapter
            ,moistureContentAdapter,exposureAdapter,typeAdapter,airTypeAdapter,slumpAdapter,maxSizeAdapter;

    private TextInputLayout tiTitle,tiSpGrCA,tiSpGrFA,tiFM,tiBulkDensity;
    private EditText etTitle,etSpGrCA,etSpGrFA,etFM,etBulkDensity;


    private DesignFormPresenter mPresenter;


    public DesignFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new DesignFormPresenter(this);

        stnAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.stn_array));
        maxSizeAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.max_size_array));
        absorpCaAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.ca_absorption_array));
        moistureContentAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.fa_moisture_array));
        exposureAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.exposure_array));
        typeAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.concrete_type_array));
        airTypeAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.air_concrete_type_array));
        slumpAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.slump_range));
    }


    /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_design_form, container, false);
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_design_form, null);
        initView(view);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialog).create();
        //AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.ThemeOverlay_AppCompat_Dialog);
        alertDialog.setView(view);
        return alertDialog;
    }

    private void initView(View view) {
        tvCancel = view.findViewById(R.id.cancel);
        tvSave = view.findViewById(R.id.save);

        tvCancel.setOnClickListener(this);
        tvSave.setOnClickListener(this);

        spDesignStn = view.findViewById(R.id.sp_design_stn);
        spMaxSizeOfAggregate = view.findViewById(R.id.sp_max_size_of_aggregate);
        spAbsorptionCapacityofCA = view.findViewById(R.id.sp_absoption_capacity_of_ca);
        spSurfaceMoistureOfFA = view.findViewById(R.id.sp_absoption_capacity_of_fa);
        spExposure = view.findViewById(R.id.sp_exposure);
        spConcreteType = view.findViewById(R.id.sp_concrete_type);
        spAirType = view.findViewById(R.id.sp_air_entrained);
        spSlump = view.findViewById(R.id.sp_slump);

        spDesignStn.setAdapter(stnAdapter);
        spMaxSizeOfAggregate.setAdapter(maxSizeAdapter);
        spAbsorptionCapacityofCA.setAdapter(absorpCaAdapter);
        spSurfaceMoistureOfFA.setAdapter(moistureContentAdapter);
        spExposure.setAdapter(exposureAdapter);
        spConcreteType.setAdapter(typeAdapter);
        spSlump.setAdapter(slumpAdapter);
        spAirType.setAdapter(airTypeAdapter);

        tiTitle = view.findViewById(R.id.ti_title);
        tiSpGrCA = view.findViewById(R.id.ti_sp_gr_coarse);
        tiSpGrFA = view.findViewById(R.id.ti_sp_gr_fine);
        tiFM = view.findViewById(R.id.ti_fineness_modulus);
        tiBulkDensity = view.findViewById(R.id.ti_dry_rodded_bulk_density);

        etTitle = view.findViewById(R.id.et_title);
        etSpGrCA = view.findViewById(R.id.et_sp_gr_coarse);
        etSpGrFA = view.findViewById(R.id.et_sp_gr_fine);
        etFM = view.findViewById(R.id.et_fineness_modulus);
        etBulkDensity = view.findViewById(R.id.et_dry_rodded_bulk_density);

        mPresenter.defaultSelection();




    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.cancel:
                dismiss();
                break;

            case R.id.save:
                String title = etTitle.getText().toString().trim();
                String spCaTxt = etSpGrCA.getText().toString().trim();
                String spFaTxt = etSpGrFA.getText().toString().trim();
                String spFMTxt = etFM.getText().toString().trim();
                String spBulkDensityTxt = etBulkDensity.getText().toString().trim();

                Data data = new Data(title);

                try{
                    double spCa = Double.parseDouble(spCaTxt);
                    double spFa = Double.parseDouble(spFaTxt);
                    double spFM = Double.parseDouble(spFMTxt);
                    double spBulkDensity = Double.parseDouble(spBulkDensityTxt);

                    data.setSp_gr_ca(spCa);
                    data.setSp_gr_fa(spFa);
                    data.setFm_fa(spFM);
                    data.setBulk_density_fa(spBulkDensity);

                }catch (Exception e){
                    return;
                }

                data.setDesign_stn(spDesignStn.getSelectedItemPosition());
                data.setMaz_size_ca(spMaxSizeOfAggregate.getSelectedItemPosition());
                data.setSlump_type(spSlump.getSelectedItemPosition());
                data.setConcrete_type(spConcreteType.getSelectedItemPosition());
                data.setConcrete_air_type(spAirType.getSelectedItemPosition());
                data.setExposure(spExposure.getSelectedItemPosition());
                data.setAbsorption_capacity_of_ca(spAbsorptionCapacityofCA.getSelectedItemPosition());
                data.setSurface_moisture_of_fa(spSurfaceMoistureOfFA.getSelectedItemPosition());
                break;
        }

    }

    @Override
    public void defaultSelection() {
        spDesignStn.setSelection(4);
        spMaxSizeOfAggregate.setSelection(2);
        spAbsorptionCapacityofCA.setSelection(1);
        spSurfaceMoistureOfFA.setSelection(3);
        spExposure.setSelection(1);
        spConcreteType.setSelection(1);
    }
}
