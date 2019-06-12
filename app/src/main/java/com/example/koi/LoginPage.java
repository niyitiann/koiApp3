package com.example.koi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

public class LoginPage extends AppCompatActivity
{
    private static final int RC_SIGN_IN = 101;

    ImageButton googleButton, facebookButton, instagramButton, twitterButton ;
    SignInButton signInButton;
    ImageView image;
    TextView text;
    GoogleSignInClient mGoogleSignInClient;

    EditText username;
    EditText password;
    EditText email;
    Button signin;
    Button register;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        signInButton = findViewById(R.id.imageButton4);
        instagramButton = findViewById(R.id.imageButton3);
        image = findViewById(R.id.logo);
        text = findViewById(R.id.text);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signIn);
        register = findViewById(R.id.register);

        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),
                        password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginPage.this,"Registered successfully", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(LoginPage.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),
                        password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(LoginPage.this,HomePage.class));
                        }else
                        {
                            Toast.makeText(LoginPage.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        // Configure Google Sign In

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        //if (firebaseAuth.getCurrentUser()!= null) {
          //  FirebaseUser user = firebaseAuth.getCurrentUser();
            //updateUI(user);
        //}
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
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            Intent intent = new Intent(getApplicationContext(), SignatureMacchiatoPage.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(),"User Logged in successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),"User Log in failed", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo = String.valueOf(user.getPhotoUrl());

            text.append("Info : \n");
            text.append(name + "\n");
            text.append(email);

            Picasso.get().load(photo).into(image);
            googleButton.setVisibility(View.INVISIBLE);

        }else {
            text.setText(getString(R.string.firebase_login));
            Picasso.get().load(R.drawable.ic_firebase_logo).into(image);
            googleButton.setVisibility(View.VISIBLE);
        }
    }
}
