package com.linearbd.mixdesign.ui.designForm;

import com.linearbd.mixdesign.model.Data;

public interface DesignFormContract {

    interface Presenter{
        void defaultSelection();
        void save(Data data);

        boolean validate(Data data);
    }

    interface View{
        void defaultSelection();

        void clearPreError();
        void showError(String message,int field);

        void complete();
    }
}
