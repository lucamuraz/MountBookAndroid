package com.example.mountbook.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.example.mountbook.R;
import com.example.mountbook.SaveSharedPreferences;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 12;
    private static final String TAG = "ERRORE";
    String username;
    String password;
    private CardView cardView;
    private ProgressBar progressBar;
    private Button loginButton;
    private final Context ctx=this;
    private TextView registration;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar=findViewById(R.id.progressBar);
        cardView=findViewById(R.id.login_form);
        cardView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        loginButton=findViewById(R.id.login);
        registration=findViewById(R.id.registrazione);
        signInButton=findViewById(R.id.sign_in_button);

        loginButton.setOnClickListener(view -> {
            cardView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            EditText u = findViewById(R.id.username);
            EditText p = findViewById(R.id.password);
            username = u.getText().toString();
            password = p.getText().toString();
            SaveSharedPreferences.setUserName(ctx, username);
            SaveSharedPreferences.setUserPassword(ctx, password);

            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("redirect", 0);
            startActivity(i);
        });

        registration.setOnClickListener(view -> {
            Intent i = new Intent(this, RegistrationActivity.class);
            startActivity(i);
        });


        /***************** GOOGLE SIGN IN ***********************/
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button) {
            signIn();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            SaveSharedPreferences.setUserName(this,account.getEmail());
            SaveSharedPreferences.setAuthMode(this, "google");

            // Signed in successfully, show authenticated UI.
            String toastMessage = "Login effettuato con successo";
            Toast mToast = Toast.makeText(ctx, toastMessage, Toast.LENGTH_LONG);
            mToast.show();
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("redirect", 0);
            startActivity(i);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            String toastMessage = "Login fallito. Riprovare";
            Toast mToast = Toast.makeText(ctx, toastMessage, Toast.LENGTH_LONG);
            mToast.show();
        }
    }
}
