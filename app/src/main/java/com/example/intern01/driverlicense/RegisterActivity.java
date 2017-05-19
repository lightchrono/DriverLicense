package com.example.intern01.driverlicense;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    FirebaseClass firebase = null;
    Activity activity;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context=this;
        activity=this;

        init();

        Button registerB = (Button) findViewById(R.id.confirmRegisterB);
        Button cancelB = (Button) findViewById(R.id.cancelRegisterB);
        final EditText email = (EditText) findViewById(R.id.registerETEmail);
        final EditText password = (EditText) findViewById(R.id.registerETPassword);

        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Test if string is empty
                String em=email.getText().toString();
                String pwd=password.getText().toString();
                if ((em.matches("")) && (pwd.matches(""))) {
                    InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    Toast.makeText(activity, "Please enter credentials", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((em.matches(""))) {
                    InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    Toast.makeText(activity, "Please enter a username", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((pwd.matches(""))) {
                    InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    Toast.makeText(activity, "Please enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        register(email.getText().toString(),password.getText().toString());
                    }
                }).start();
            }
        });

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
    private FirebaseAuth auth=null;
    private FirebaseDatabase db=null;

    private boolean init(){
        try{
            auth=FirebaseAuth.getInstance();
        }
        catch (Exception authEx){
            return false;
        }
        try{
            db=FirebaseDatabase.getInstance();
        }
        catch (Exception dbEx){
            return false;
        }
        if((auth==null)||(db==null)){
            return false;
        }
        return true;
    }
    private void register(String email,String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("firebase", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Account not created", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
