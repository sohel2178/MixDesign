package com.linearbd.mixdesign.ui.mixDesign.calculation;

import com.linearbd.mixdesign.model.Data;

public class CalculationPresenter implements CalculationContract.Presenter {

    private CalculationContract.View mView;

    public CalculationPresenter(CalculationContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void initialize(Data data) {
        mView.initialize(data);
    }
}
