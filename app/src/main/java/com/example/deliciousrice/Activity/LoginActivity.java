package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private TextView tvTextDangKy, tvDangNhap, tvQuenMK;
    private ImageView ivBackpa;
    private ConstraintLayout ctlGoogle, ctlFacebook;
    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BarColor.setStatusBarColor(this);

        tvTextDangKy = findViewById(R.id.tvTextDangky);
        tvDangNhap = findViewById(R.id.tvDangNhap);
        tvQuenMK = findViewById(R.id.tvQuenMK);
        ivBackpa = findViewById(R.id.ivBackpaa);
        ctlFacebook = findViewById (R.id.ctlFacebook);
        ctlGoogle = findViewById (R.id.ctlGoogle);

        ivBackpa.setOnClickListener(v -> manLogin());
        tvDangNhap.setOnClickListener(v -> dangNhap());
        tvTextDangKy.setOnClickListener(v -> dangKy());
        tvQuenMK.setOnClickListener(v -> quenMK());
        loginFB ();
        loginGoogle ();
    }

    private  void manLogin(){
        Intent intent = new Intent(LoginActivity.this, LoginFaGoActivity.class);
        startActivity(intent);

    }

    private void dangNhap(){
        Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
        startActivity(intent);
    }

    private void dangKy(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void quenMK(){
        Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
        startActivity(intent);
    }
    private void loginFB(){
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult> () {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        startActivity (new Intent (LoginActivity.this, MainActivity2.class));
                        Toast.makeText (LoginActivity.this, "đăng nhập thành công", Toast.LENGTH_SHORT).show ();
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
        ctlFacebook.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
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
            startActivity(new Intent(LoginActivity.this,MainActivity2.class));
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }
    public void loginGoogle(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        cvLoginGoogle.setSize(SignInButton.SIZE_STANDARD);
        ctlGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ctlGoogle:
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