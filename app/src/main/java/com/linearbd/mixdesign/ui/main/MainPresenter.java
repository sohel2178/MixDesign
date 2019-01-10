package com.linearbd.mixdesign.ui.main;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;
import com.linearbd.mixdesign.database.MyDatabaseRef;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private FirebaseUser mCurrentUser;
    private MyDatabaseRef myDatabaseRef;

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
        this.mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.myDatabaseRef = new MyDatabaseRef();
    }

    @Override
    public void checkCurrentUser() {
        if(mCurrentUser==null){
            Log.d("User","User Null");
            mView.startLoginActivity();
        }else {
            Log.d("User","User Not Null");
            mView.initView();
        }
    }

    @Override
    public void requestForAd(int action) {
        mView.showAd(action);
    }

    @Override
    public void buttonClick(int action) {
        switch (action){
            case 1:
                mView.showForm();
                break;
            case 2:
                mView.showDocumentation();
                break;
        }
    }

    @Override
    public void initAdapter() {
       Query query= myDatabaseRef.getMixDesignRef().orderByChild("uid").equalTo(mCurrentUser.getUid());
       mView.initAdapter(query);
    }
}
