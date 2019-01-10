package com.linearbd.mixdesign.ui.main;

import com.google.firebase.database.Query;

public interface MainContract {

    interface Presenter{
        void checkCurrentUser();
        void requestForAd(int action);
        void buttonClick(int action);
        void initAdapter();
    }

    interface View{
        void startLoginActivity();
        void initView();
        void showForm();
        void showDocumentation();
        void showAd(int action);
        void initAdapter(Query query);
    }
}
