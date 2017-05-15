package com.example.intern01.driverlicense;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by intern01 on 15/05/2017.
 */





class FirebaseClass {
    FirebaseDatabase db=null;

    public Integer dbConnect(){

        /*
        -2 - database already connected
        -1 - could not connect to database
         0 - connected to database

        */

        if(db!=null){
            return -2;
        }
        try{
            db=FirebaseDatabase.getInstance();

        }catch (Exception ex){
            return -1;
        }

        return 0;
    }
    public void test(){
        DatabaseReference myRef=db.getReference("message");
        myRef.setValue("HelloWorld");

    }


}
