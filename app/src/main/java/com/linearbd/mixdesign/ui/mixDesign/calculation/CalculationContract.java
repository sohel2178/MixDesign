package com.linearbd.mixdesign.ui.mixDesign.calculation;

import com.linearbd.mixdesign.model.Data;

public interface CalculationContract {

    interface Presenter{

        void initialize(Data data);

    }

    interface View{
        void initialize(Data data);
    }


}
