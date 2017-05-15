package com.example.intern01.driverlicense;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

/**
 * Created by intern01 on 15/05/2017.
 */

        /*
        -2 - database already connected
        -1 - could not connect to database
         0 - connected to database

        */




class FirebaseClass {
    private FirebaseDatabase db=null;
    private FirebaseAuth auth=null;
    private FirebaseAuth.AuthStateListener authListener=null;

    public Integer dbConnect(){


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

    public Integer authConnect(){
        if(auth!=null){
            return -2;
        }
        try{
            auth=FirebaseAuth.getInstance();
        }catch (Exception ex) {
            return -1;
        }
        return 0;
    }

    public void test(){
        DatabaseReference obj=db.getReference("users");
        DatabaseReference username=obj.child("username");
        username.setValue("Hello");


    }

    public void createAccount(String email, String password){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("TAG","createUserWithEmail:onComplete: "+task.isSuccessful());
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        // User is signed in
                        Log.d("auth", "onAuthStateChanged:signed_in:" + user.getUid());
                    } else {
                        // User is signed out
                        Log.d("auth", "onAuthStateChanged:signed_out");
                    }
                }
            }
        });
    }


}
