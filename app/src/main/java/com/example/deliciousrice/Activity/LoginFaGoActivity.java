package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class LoginFaGoActivity extends AppCompatActivity {
    private CardView cvLoginGoogle;
    private CardView cvLoginFacebook;

    private TextView tvLoginDangNhap;

    private TextView tvTextLoginDangky;

    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fa_go);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication ());
        cvLoginGoogle = findViewById(R.id.cvLoginGoogle);
        cvLoginFacebook = findViewById(R.id.cvLoginFacebook);

        tvLoginDangNhap = findViewById(R.id.tvLoginDangNhap);
        tvTextLoginDangky = findViewById(R.id.tvTextLoginDangky);

        tvLoginDangNhap.setOnClickListener( v -> loginDangNhap());
        tvTextLoginDangky.setOnClickListener(v -> manDangKy());
        loginFacebook ();
        loginGoogle ();

    }
    private  void loginDangNhap(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);

    }
    private  void  manDangKy(){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void loginFacebook(){
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult> () {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        startActivity (new Intent (LoginFaGoActivity.this, MainActivity2.class));
                        Toast.makeText (LoginFaGoActivity.this, "đăng nhập thành công", Toast.LENGTH_SHORT).show ();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        cvLoginFacebook.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginFaGoActivity.this, Arrays.asList("public_profile"));
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
            }
            startActivity(new Intent(LoginFaGoActivity.this,MainActivity2.class));
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }
    private void loginGoogle(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        cvLoginGoogle.setSize(SignInButton.SIZE_STANDARD);
        cvLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.cvLoginGoogle:
                        signIn();
                        break;

                }
            }
        });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }
}