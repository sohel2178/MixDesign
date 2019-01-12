package com.linearbd.mixdesign.ui.mixDesign.info;

import com.linearbd.mixdesign.model.Data;

public interface InfoContract {

    interface Presenter{
        void initialize(Data data);
    }

    interface View{
        void initialize(Data data);
    }
}
