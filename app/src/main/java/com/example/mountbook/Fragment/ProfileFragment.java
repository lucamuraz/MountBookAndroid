package com.example.mountbook.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mountbook.Activity.LoginActivity;
import com.example.mountbook.Activity.MainActivity;
import com.example.mountbook.AppManager;
import com.example.mountbook.R;
import com.example.mountbook.SaveSharedPreferences;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

public class ProfileFragment extends Fragment {

    private final Context ctx= AppManager.getInstance().getCtx();

    private TextView user;
    private CardView logout;
    private GoogleSignInClient mGoogleSignInClient;

    public ProfileFragment() {
        // Required empty public constructor
    }
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        user=rootView.findViewById(R.id.user);
        user.setText(SaveSharedPreferences.getUserName(ctx));
        logout=rootView.findViewById(R.id.card_logout);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(ctx, gso);

        logout.setOnClickListener(view -> {
            if(SaveSharedPreferences.getAuthMode(ctx).equals("google")){
                signOut();
            }else if(SaveSharedPreferences.getAuthMode(ctx).equals("facebook")){

            }else{
                SaveSharedPreferences.clearData(ctx);
                Intent i = new Intent(ctx, LoginActivity.class);
                startActivity(i);
                requireActivity().finish();
            }
        });

        return rootView;

    }

    private void signOut() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SaveSharedPreferences.clearData(ctx);
                        Intent i = new Intent(ctx, LoginActivity.class);
                        startActivity(i);
                        requireActivity().finish();
                    }
                });
    }

}
