package com.linearbd.mixdesign.ui.designForm;

public class DesignFormPresenter implements DesignFormContract.Presenter {

    private DesignFormContract.View mView;

    public DesignFormPresenter(DesignFormContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void defaultSelection() {
        mView.defaultSelection();
    }
}
