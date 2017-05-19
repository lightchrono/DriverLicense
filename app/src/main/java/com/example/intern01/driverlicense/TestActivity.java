package com.example.intern01.driverlicense;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        if(getIntent().getExtras()==null) user="not";
        else {
            user=getIntent().getExtras().getString("USER");
        }

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();

        Button b=(Button)findViewById(R.id.testButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("username",user);
                DatabaseReference root=db.getReference("Users");
                DatabaseReference userRoot=root.child(user);
                DatabaseReference producer=userRoot.child("producer");producer.setValue("");


            }
        });
    }
}
