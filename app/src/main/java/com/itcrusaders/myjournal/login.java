package com.itcrusaders.myjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


public class login extends AppCompatActivity {


    private static int RC_SIGN_IN =123;
    private List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        {

            Intent intent = new Intent(this, journal_display.class);

            startActivity(intent);
        }
        else
        {
            attemptLogin();
        }


        Button mSignIn = (Button) findViewById(R.id.btn_submit);

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {

        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        startActivityForResult(intent,
                RC_SIGN_IN);


    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data )
    {
        super.onActivityResult(requestCode,resultCode,data);


        if(requestCode == RC_SIGN_IN)
        {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                Intent intenth = new Intent(this, journal_display.class);

                startActivity(intenth);
                // ...
            } else {
                Toast.makeText(this,"Error!!! User does not exits.",Toast.LENGTH_LONG).show();
            }

        }
    }




}
