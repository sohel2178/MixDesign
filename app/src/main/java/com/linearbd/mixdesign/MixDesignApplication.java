package com.linearbd.mixdesign;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class MixDesignApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
    }


}
