package com.example.restro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.List;

public class Login_page_praent extends AppCompatActivity {
    Bundle bundle;
    AppDatabase db;
    SignInButton btnGoogle;
    Button btnLogin;
    TextView txtregister;
    TextView txtContinue;
    TextInputLayout txtEmailName;
    TextInputLayout Password;
    TextView txtLogin;
    FirebaseAuth mAuth;
    Sprite doubleBounce;
    ProgressBar progressBar;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    public GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().hide();
        db = restaurant_info.buildDB(this);

        mAuth = FirebaseAuth.getInstance();

        btnGoogle = (SignInButton) findViewById(R.id.sign_in_button);
        btnLogin = findViewById(R.id.btnLogin);
        txtregister = findViewById(R.id.txtregister);
        txtLogin = findViewById(R.id.txtLogin);
        txtContinue = findViewById(R.id.txtContinue);
        Password = findViewById(R.id.txtpassword);
        txtEmailName = findViewById(R.id.txtEmail);
        txtregister.setText("Register");
        txtContinue.setText("Continue with:");
        setUnderLineText(txtContinue, "Continue with:");
        setUnderLineText(txtLogin, "Log in for the best experience");
        setUnderLineText(txtregister, "Register");
// Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        progressBar = findViewById(R.id.spin_kit);
        progressBar.setVisibility(View.INVISIBLE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        if (!isNetworkConnected()) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(Login_page_praent.this);
            builder1.setTitle("Alert");
            builder1.setMessage("Please Provide Internet Connection");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                            finish();
                        }
                    });

            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }


        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean Email=false;
                List<UserEntities> res = db.U_Dao().getAll();
                for (int i =0;i<res.size();i++){
                    if (res.get(i).U_Email.equals(txtEmailName.getEditText().getText().toString()) &&res.get(i).U_Pass.equals(Password.getEditText().getText().toString())){
                        Email = true;
                    }
                }

                if (Email && Password.getEditText().getText().toString().equals(res.get(0).U_Pass)) {
                    Intent i = new Intent(Login_page_praent.this, HomeFragmentParent.class);
                    bundle = new Bundle();
                    bundle.putString("Name", "");
                    bundle.putString("Email", "");
                    bundle.putString("Phno", "");
                    i.putExtras(bundle);
                    EditSharePRef("in");
                    startActivity(i);

                } else {
                    Toast.makeText(Login_page_praent.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        txtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new registrationPage_frag();
                FragmentManager fragmentManager = getSupportFragmentManager();
                bundle = new Bundle();
                bundle.putString("Name", "");
                bundle.putString("Email", "");
                bundle.putString("Phno", "");
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.Login_parent, fragment).addToBackStack(String.valueOf(fragmentManager)).commit();

            }
        });

    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        doubleBounce = new FadingCircle();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.VISIBLE);
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(getApplicationContext(), "Api Exception", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String Email = mAuth.getCurrentUser().getEmail();
                            String Name = mAuth.getCurrentUser().getDisplayName();
                            String PhNo = mAuth.getCurrentUser().getPhoneNumber();

                            updateUI(user, Email, Name, PhNo);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null, "", "", "");

                        }
                    }
                });
    }


//    USE THIS IN SPlASH SCREEN
//    @Override
//    protected void onStart() {
//        // Check for existing Google Sign In account, if the user is already signed in
//// the GoogleSignInAccount will be non-null.
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//
//    }

    void updateUI(FirebaseUser user, String Email, String Name, String Phno) {

        if (user != null) {
            //Home page  Intent
            //Register page Intent
            boolean mail=false;
            List<UserEntities> res = db.U_Dao().getAll();
            for (int i =0;i<res.size();i++){
                if (res.get(i).U_Email.equals(txtEmailName.getEditText().getText().toString()) &&res.get(i).U_Pass.equals(Password.getEditText().getText().toString())){
                    mail = true;
                }
            }

            bundle = new Bundle();
            bundle.putString("Name", Name);
            bundle.putString("Email", Email);
            bundle.putString("Phno", Phno);
            if (mail) {

                Intent i = new Intent(Login_page_praent.this, HomeFragmentParent.class);

                i.putExtras(bundle);
                EditSharePRef("in");
                startActivity(i);
                finish();
                progressBar.setVisibility(View.INVISIBLE);
            } else {
                Fragment fragment = new registrationPage_frag();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragment.setArguments(bundle);
                progressBar.setVisibility(View.INVISIBLE);
                fragmentManager.beginTransaction().replace(R.id.Login_parent, fragment).addToBackStack(String.valueOf(fragmentManager)).commit();


            }


        } else {
            Toast.makeText(this, "Error Signing In Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }, 2080);


        }
    }

    public static void setUnderLineText(TextView tv, String textToUnderLine) {
        String tvt = tv.getText().toString();
        int ofe = tvt.indexOf(textToUnderLine, 0);

        UnderlineSpan underlineSpan = new UnderlineSpan();
        SpannableString wordToSpan = new SpannableString(tv.getText());
        for (int ofs = 0; ofs < tvt.length() && ofe != -1; ofs = ofe + 1) {
            ofe = tvt.indexOf(textToUnderLine, ofs);
            if (ofe == -1)
                break;
            else {
                wordToSpan.setSpan(underlineSpan, ofe, ofe + textToUnderLine.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    public void EditSharePRef(String status) {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putString("LOG", status);
        myEdit.apply();

    }

}