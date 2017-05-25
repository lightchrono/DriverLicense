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

import com.example.intern01.driverlicense.activity.MainActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Context context;
    boolean test = true;
    Activity activity;
    boolean isOn = false;
    Button loginB;
    //Google
    SignInButton googleLoginB;
    //Facebook
    LoginButton facebookloginB;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        activity = this;
        loginB = (Button) findViewById(R.id.loginB);

        // Configure Google Sign In
        googleLoginB = (SignInButton) findViewById(R.id.loginGoogle);
        TextView textView = (TextView) googleLoginB.getChildAt(0);
        textView.setText("Continue with Google");

        googleLoginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent googleIntent = new Intent(context, GoogleLogin.class);
                startActivity(googleIntent);
            }
        });

        // Configure Facebook Sign In
        mCallbackManager = CallbackManager.Factory.create();
        facebookloginB = (LoginButton) findViewById(R.id.loginFacebook);
        facebookloginB.setReadPermissions("email", "public_profile");
        facebookloginB.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Facebook", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Facebook", "facebook:onCancel");
                Toast.makeText(activity, "You have canceled the login.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Facebook", "facebook:onError", error);
                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        if (init()) {
            Log.d("firebase", "Connection established");
        } else {
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

        Button registerB = (Button) findViewById(R.id.registerB);

        final Intent registerI = new Intent(this, RegisterActivity.class);

        registerB.setOnClickListener(new View.OnClickListener() {
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
                Intent test = new Intent(getBaseContext(), TestActivity.class);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(test);
            }
        });

        onStart();

    }

    private FirebaseAuth auth = null;

    private boolean init() {
        auth = FirebaseAuth.getInstance();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FB", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FB", "signInWithCredential:success");
                            Intent main = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(main);
                            Toast.makeText(activity, "You have been authenticated.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FB", "signInWithCredential:failure", task.getException());
                            Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser==null){
        }
        else {
            Intent main = new Intent(getBaseContext(), MainActivity.class);
            startActivity(main);
            finish();
        }
    }

}