package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Customer;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmailDangNhap;
    private EditText edtPassWordDangNhap;
    private ConstraintLayout ctlGoogle, ctlFacebook;
    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BarColor.setStatusBarColor(this);
        checkBox = findViewById(R.id.ckbNhoMK);
        edtEmailDangNhap = findViewById(R.id.edtEmailDangNhap);
        edtPassWordDangNhap = findViewById(R.id.edtPassWordDangNhap);
        ctlFacebook = findViewById(R.id.ctlFacebook);
        ctlGoogle = findViewById(R.id.ctlGoogle);
//        loginFB();
//        loginGoogle();
        getPreferences();
    }

    private void loginFB() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        startActivity(new Intent(LoginActivity.this, MainActivity2.class));
                        Toast.makeText(LoginActivity.this, "đăng nhập thành công", Toast.LENGTH_SHORT).show();
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
        ctlFacebook.setOnClickListener(new View.OnClickListener() {
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
            startActivity(new Intent(LoginActivity.this, MainActivity2.class));
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void loginGoogle() {
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

    //Login
    public void onClickLogin(View view) {
        if (!validateemail() | !validatepass()) {
            return;
        } else {
            String apilogin = "https://appsellrice.000webhostapp.com/Deliciousrice/API/Login.php";
            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();
            String strname = edtEmailDangNhap.getText().toString().trim();
            String strpass = edtPassWordDangNhap.getText().toString().trim();
            StringRequest request = new StringRequest(Request.Method.POST, apilogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    if (response.equalsIgnoreCase("Đăng Nhập Thành Công")) {
                        remember(strname, strpass, checkBox.isChecked());
                        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Email or password wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", strname);
                    params.put("password", strpass);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        }
    }

    public boolean validateemail() {
        String a = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (edtEmailDangNhap.getText().toString().equals("")) {
            edtEmailDangNhap.setError("Hãy nhập gmail của bạn.");
            return false;
        } else if (!edtEmailDangNhap.getText().toString().trim().matches(a)) {
            edtEmailDangNhap.setError("Nhập đúng định dạng gmail.");
            return false;
        } else {
            edtEmailDangNhap.setError(null);
            return true;
        }
    }

    public boolean validatepass() {
        if (edtPassWordDangNhap.getText().toString().equals("")) {
            edtPassWordDangNhap.setError("Nhập mật khẩu của bạn");
            return false;
        } else {
            edtPassWordDangNhap.setError(null);
            return true;
        }
    }

    public void getPreferences() {
        SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
        edtEmailDangNhap.setText(preferences.getString("gmail", ""));
        edtPassWordDangNhap.setText(preferences.getString("matkhau", ""));
        checkBox.setChecked(preferences.getBoolean("remember", false));
    }

    private void remember(String strname, String strpass, boolean checked) {
        SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!checked) {
            editor.clear();
        } else {
            editor.putString("gmail", strname);
            editor.putString("matkhau", strpass);
            editor.putBoolean("remember", checked);
        }
        editor.commit();
    }

    //register
    public void onClickRegisteraccount(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    //forgotpassword
    public void onClickForgotPassword(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
        startActivity(intent);
    }

    //backsceen
    public void onClickBackSceen(View view) {
        Intent intent = new Intent(LoginActivity.this, LoginFaGoActivity.class);
        startActivity(intent);
    }

}