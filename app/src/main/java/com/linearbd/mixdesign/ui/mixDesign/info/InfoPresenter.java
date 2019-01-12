package com.linearbd.mixdesign.ui.mixDesign.info;

import com.linearbd.mixdesign.model.Data;

public class InfoPresenter implements InfoContract.Presenter {

    private InfoContract.View mView;

    public InfoPresenter(InfoContract.View mView) {
        this.mView = mView;
    }


    @Override
    public void initialize(Data data) {
        mView.initialize(data);
    }
}
