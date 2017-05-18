package com.example.intern01.driverlicense;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import com.example.intern01.driverlicense.activity.MainActivity;
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

    public Integer init(){


        if((db!=null)||(auth!=null)||(authListener!=null)){
            return -2;
        }
        try{
            db=FirebaseDatabase.getInstance();
            auth=FirebaseAuth.getInstance();
            authListener=new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    if(user!=null)
                    {
                        Log.d("firebase", "onAuthStateChanged:signed_in:" + user.getUid());

                    }else{
                        Log.d("firebase", "onAuthStateChanged:signed_out");

                    }
                }
            };


        }catch (Exception ex){
            return -1;
        }

        return 0;
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

    public void loginFirebase(){


    }

    boolean login(Activity activity){
        final boolean[] ifFound = {false};
        auth.signInWithEmailAndPassword("t@t.com","123123").addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("firebase", "signInWithEmail:onComplete:" + task.isSuccessful());
                ifFound[0] =true;
            }
        });

        Log.d("firebaseClass","ifFound= "+ifFound[0]);
        if (ifFound[0]) {
            return true;
        }
        return false;
    }

}
