package com.linearbd.mixdesign.ui.mixDesign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.linearbd.mixdesign.R;
import com.linearbd.mixdesign.model.Data;

public class BaseFragment extends Fragment {
    private MixDesignActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MixDesignActivity) getActivity();
    }

    public Data getData(){
        return activity.getData();
    }

    public String getDesignStn(){
        int pos = getData().getDesign_stn();
        return getResources().getStringArray(R.array.stn_array)[pos];
    }
}
