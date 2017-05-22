package com.example.intern01.driverlicense;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.example.intern01.driverlicense.activity.MainActivity;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    Context context;
    boolean test = true;
    Activity activity;
    boolean isOn = false;
    Button loginB;
    //Google Log In
    SignInButton googleLoginB;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    //Facebook
    LoginButton facebookloginB;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        context = this;
        activity = this;
        loginB = (Button) findViewById(R.id.loginB);

        if (init()) {
            Log.d("firebase", "Connection established");
        } else {
            db = null;
            auth = null;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Could not connect to server").setTitle("Connection Error").setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LoginActivity.super.finish();
                        }
                    });
            builder.show();
        }

        // Configure Google Sign In
        googleLoginB = (SignInButton) findViewById(R.id.loginGoogle);
        TextView textView = (TextView) googleLoginB.getChildAt(0);
        textView.setText("Continue with Google");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(activity, "Error while trying to connect with Google.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleLoginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        //Configure Facebook Sign In
        facebookloginB = (LoginButton) findViewById(R.id.loginFacebook);
        callbackManager = CallbackManager.Factory.create();

        TextView registerTV = (TextView) findViewById(R.id.registerTV);

        final Intent registerI = new Intent(this, RegisterActivity.class);

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registerI);
            }
        });

        final EditText username = (EditText) findViewById(R.id.loginETUsername);
        final EditText password = (EditText) findViewById(R.id.loginETPassword);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Test if string is empty
                String uname = username.getText().toString();
                String pwd = password.getText().toString();
                if ((uname.matches("")) && (pwd.matches(""))) {
                    InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    Toast.makeText(activity, "Please enter credentials", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((uname.matches(""))) {
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

                login(username.getText().toString(), password.getText().toString());
                loginB.setEnabled(false);
            }
        });

        ImageView imgTest = (ImageView) findViewById(R.id.imageViewTest);
        imgTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login("t@t.com", "123123");
                Intent test = new Intent(getBaseContext(), TestActivity.class);
                test.putExtra("USER", String.valueOf(auth.getCurrentUser().getUid()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(test);
            }
        });
    }

    private FirebaseAuth auth = null;
    private FirebaseDatabase db = null;

    private boolean init() {

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();


        return true;
    }

    private void login(final String email, final String password) {
        Log.d("firebase", "firebase");
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("firebase2", "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {

                            getSharedPreferences("loginC", MODE_PRIVATE)
                                    .edit()
                                    .putString("lc_email", email)
                                    .putString("lc_pass", password)
                                    .apply();

                            Intent main = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(main);
                            finish();
                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("firebase", "signInWithEmail:failed", task.getException());
                            Toast.makeText(activity, "Authentication failed", Toast.LENGTH_SHORT).show();
                            loginB.setEnabled(true);
                        }

                        // ...
                    }
                });
    }

    //Google Log In
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("firebase", "log with google");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            Intent main = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(main);
                            finish();
                        } else {
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            loginB.setEnabled(true);
                        }
                    }
                });
    }
}