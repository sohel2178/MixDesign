package com.linearbd.mixdesign.database;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by sohel on 07-02-18.
 */

public class MyDatabaseRef {
    private static final String USER_REF="Users";
    private static final String MIX_DESIGN="mix_design";

    private static MyDatabaseRef instance;



    private FirebaseDatabase database;

    public MyDatabaseRef() {
        this.database  = FirebaseDatabase.getInstance();
    }


    public DatabaseReference getUserRef(){
        return database.getReference(USER_REF);
    }

    public DatabaseReference getMixDesignRef(){
        return database.getReference(MIX_DESIGN);
    }

}
