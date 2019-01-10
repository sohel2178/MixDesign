package com.linearbd.mixdesign.ui.designForm;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.linearbd.mixdesign.database.MyDatabaseRef;
import com.linearbd.mixdesign.model.Data;

public class DesignFormPresenter implements DesignFormContract.Presenter {

    private DesignFormContract.View mView;
    private FirebaseUser mCurrentuser;
    private MyDatabaseRef myDatabaseRef;

    public DesignFormPresenter(DesignFormContract.View mView) {

        this.mView = mView;
        this.mCurrentuser = FirebaseAuth.getInstance().getCurrentUser();
        this.myDatabaseRef = new MyDatabaseRef();
    }

    @Override
    public void defaultSelection() {
        mView.defaultSelection();
    }

    @Override
    public void save(Data data) {
       //Log.d("HHHH","Everything OK");
        data.setUid(mCurrentuser.getUid());

        String id =myDatabaseRef.getMixDesignRef().push().getKey();
        data.setId(id);

        myDatabaseRef.getMixDesignRef().child(id).setValue(data, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                mView.complete();
            }
        });


    }

    @Override
    public boolean validate(Data data) {
        mView.clearPreError();

        Log.d("HHHH","Call Out");

        if(data.getTitle().equals("")){
            mView.showError("Mix Design Title Required",1);
            return false;
        }

        if(data.getSp_gr_ca()==0){
            mView.showError("Specific Gravity of CA is Required",2);
            return false;
        }

        if(data.getSp_gr_fa()==0){
            mView.showError("Specific Gravity of FA is Required",3);
            return false;
        }

        if(data.getFm_fa()==0){
            mView.showError("Fineness Modulus of FA is Required",4);
            return false;
        }

        if(data.getBulk_density_ca()==0){
            mView.showError("Bulk Density of CA is Required",5);
            return false;
        }
        return true;
    }
}
