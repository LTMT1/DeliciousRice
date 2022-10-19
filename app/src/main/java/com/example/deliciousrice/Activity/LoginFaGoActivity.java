package com.example.deliciousrice.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFaGoActivity extends AppCompatActivity {
    private CardView cvLoginGoogle;
    private LoginButton loginButton;
    private TextView tvLoginDangNhap;
    private TextView tvTextLoginDangky;

    String name,firstname,picture;
    int id;
    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private long backPressTime;
    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login_fa_go);
        BarColor.setStatusBarColor(this);

        overridePendingTransition(R.anim.anim_intent_in, R.anim.anim_intent_out);
        cvLoginGoogle = findViewById(R.id.cvLoginGoogle);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        tvLoginDangNhap = findViewById(R.id.tvLoginDangNhap);
        tvTextLoginDangky = findViewById(R.id.tvTextLoginDangky);

        tvLoginDangNhap.setOnClickListener(v -> loginDangNhap());
        tvTextLoginDangky.setOnClickListener(v -> manDangKy());
        loginFacebook();
        loginGoogle();

    }
    private void loginDangNhap() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    private void manDangKy() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()){
            mToast.cancel();

            Intent intent = new Intent(getApplicationContext(), HelloScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            finish();
            System.exit(0);

        }else {
            mToast = Toast.makeText(LoginFaGoActivity.this, "Ấn lần nữa để thoát", Toast.LENGTH_SHORT);
            mToast.show();
        }
        backPressTime = System.currentTimeMillis();
    }
    public void loginFacebook() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final ProgressDialog progressDialog = new ProgressDialog(LoginFaGoActivity.this);
                progressDialog.setMessage("Please Wait..");
                progressDialog.show();
                result();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 3000);

            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    private void result() {

        GraphRequest graphRequest=GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                Log.e("ssss",graphResponse.getJSONObject().toString());
                try {
                    name=jsonObject.getString("name");
                    firstname=jsonObject.getString("first_name");
                    id=jsonObject.getInt("id");
                    picture="https://graph.facebook.com/"+id+"/picture?style=large";
                    Log.e("",""+name+firstname+picture+id);
                    InsertAccface(id,picture,name);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        Bundle bundle=new Bundle();
        bundle.putString("fields","name,email,first_name,picture");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
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
            startActivity(new Intent(LoginFaGoActivity.this, MainActivity2.class));
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

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }
    private void InsertAccface(int idf,String picturef,String namef){
        StringRequest request = new StringRequest(Request.Method.POST, "https://appsellrice.000webhostapp.com/Deliciousrice/API/RegisterFacebook.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Đăng kí Thành Công")) {
                    startActivity(new Intent(LoginFaGoActivity.this, MainActivity2.class));
                    Toast.makeText(LoginFaGoActivity.this, "đăng nhập thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("image", picturef);
                params.put("id", String.valueOf(idf));
                params.put("username", namef);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(request);
    }
}