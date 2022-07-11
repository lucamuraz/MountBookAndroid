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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 12;
    private static final String TAG = "ERRORE";
    String username;
    String password;
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
    EditText u;
    EditText p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar=findViewById(R.id.progressBar);
        cardView=findViewById(R.id.signup_form);
        cardView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        loginButton=findViewById(R.id.login);
        registration=findViewById(R.id.registrazione);
        signInButton=findViewById(R.id.sign_in_button);
        u = findViewById(R.id.username);
        p = findViewById(R.id.password);


        loginButton.setOnClickListener(view -> {
            cardView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            username = u.getText().toString();
            password = p.getText().toString();

            json.clear();
            json.add(new Pair<>("username",username));
            json.add(new Pair<>("password",password));

            url="/api/auth/signin";
            new FetchDataTask().execute();
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
            SaveSharedPreferences.setUserName(this,account.getDisplayName());
            SaveSharedPreferences.setAuthMode(this, "google");

            json.clear();
            json.add(new Pair<>("username",account.getDisplayName()));
            json.add(new Pair<>("email",account.getEmail()));
            json.add(new Pair<>("password","PasswordSegretissima"));

            u.setText(SaveSharedPreferences.getUserName(ctx));
            p.setText("PasswordSegretissima");

            url="/api/auth/signup";
            new FetchDataTask().execute(url);

            // Signed in successfully, show authenticated UI.
            String toastMessage = "Registrazione effettuata con successo";
            Toast mToast = Toast.makeText(ctx, toastMessage, Toast.LENGTH_LONG);
            mToast.show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            String toastMessage = "Login fallito. Riprovare";
//            Toast mToast = Toast.makeText(ctx, toastMessage, Toast.LENGTH_LONG);
//            mToast.show();
//            Intent i = new Intent(ctx, LoginActivity.class);
//            startActivity(i);
            u.setText(SaveSharedPreferences.getUserName(ctx));
            p.setText("PasswordSegretissima");
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
                                "B Login fallito. Riprovare",
                                Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ctx, LoginActivity.class);
                        startActivity(i);
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(String dataFetched) {
            //parse the JSON data and then display
            try{
                JSONObject js=new JSONObject(jsonString);
                SaveSharedPreferences.setUserId(ctx, String.valueOf(js.getInt("id")));
                SaveSharedPreferences.setUserName(ctx, username);
                SaveSharedPreferences.setUserPassword(ctx, password);
                Intent i = new Intent(ctx, MainActivity.class);
                i.putExtra("redirect", 0);
                startActivity(i);
            }catch(Exception e){
                Log.i("App", "Error parsing data" +e.getMessage());
            }
        }
    }
}
