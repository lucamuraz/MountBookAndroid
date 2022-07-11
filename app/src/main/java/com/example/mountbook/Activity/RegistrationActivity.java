package com.example.mountbook.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import androidx.core.util.Pair;

import com.example.mountbook.HttpHandler;
import com.example.mountbook.R;
import com.example.mountbook.SaveSharedPreferences;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int RC_SIGN_IN = 12;
    private static final String TAG = "ERRORE";
    String username;
    String password;
    String email;
    private String url;
    private CardView cardView;
    private ProgressBar progressBar;
    private Button loginButton;
    private final Context ctx=this;
    private TextView registration;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    List<Pair<String,Object>> json= new ArrayList<>();
    private String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SaveSharedPreferences.setAuthMode(this, "");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressBar=findViewById(R.id.progressBar2);
        cardView=findViewById(R.id.signup_form);
        cardView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        loginButton=findViewById(R.id.registrate);


        loginButton.setOnClickListener(view -> {
            EditText u = findViewById(R.id.username_up);
            EditText p = findViewById(R.id.password_up);
            EditText e= findViewById(R.id.email_up);
            if(!Objects.equals(SaveSharedPreferences.getAuthMode(ctx), "google")){
                u = findViewById(R.id.username_up);
                p = findViewById(R.id.password_up);
                e= findViewById(R.id.email_up);
                username = u.getText().toString();
                password = p.getText().toString();
                email = e.getText().toString();
            }else{
                u.setText(username);
                p.setText(password);
                e.setText(email);
            }
            cardView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);


            json.clear();
            json.add(new Pair<>("username",username));
            json.add(new Pair<>("email", email));
            json.add(new Pair<>("password",password));

            url="/api/auth/signup"; //todo mettere link per recuperare tutti i rifugi
            new FetchDataTask().execute();

        });

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
            SaveSharedPreferences.setUserName(this,account.getDisplayName());
            SaveSharedPreferences.setAuthMode(this, "google");

            username=account.getDisplayName();
            password="PasswordSegretissima";
            email=account.getEmail();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            String toastMessage = "Registrazione fallita. Riprovare";
            Toast mToast = Toast.makeText(ctx, toastMessage, Toast.LENGTH_LONG);
            mToast.show();
        }
    }

    private class FetchDataTask extends AsyncTask<String, Void, String> {

        private static final String TAG = "con";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Toast.makeText(ctx,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            jsonString = sh.makePostCall(url,json);
            Log.e(TAG, "Response from url: " + jsonString);

            if(jsonString == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ctx,
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(String dataFetched) {
            //parse the JSON data and then display
            if(Objects.equals(SaveSharedPreferences.getAuthMode(ctx), "google")){

                String toastMessage = "Login effettuato con successo";
                Toast mToast = Toast.makeText(ctx, toastMessage, Toast.LENGTH_LONG);
                mToast.show();
                Intent i = new Intent(ctx, MainActivity.class);
                i.putExtra("redirect", 0);
                startActivity(i);
            }else{
                String toastMessage = "Registrazione effettuato con successo";
                Toast mToast = Toast.makeText(ctx, toastMessage, Toast.LENGTH_LONG);
                mToast.show();
                Intent i = new Intent(ctx, LoginActivity.class);
                startActivity(i);
            }


        }
    }
}
