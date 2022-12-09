package com.example.deliciousrice.ui.account.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;

public class VerifyPhoneFragment extends Fragment {

    private ProgressBar progressBar;
    private EditText editText;
    String phonenumber,verificationId;
    int id;
    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify_phone, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressbar);
        editText = view.findViewById(R.id.editTextCode);
        Bundle bundle = getArguments();
         phonenumber = bundle.getString("sdt");
        id = bundle.getInt("id", 0);
        verificationId= bundle.getString("verificationId");
        auth=FirebaseAuth.getInstance();

        view.findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editText.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    editText.setError("Enter code...");
                    return;
                }
                sentOtp(code);
            }
        });
    }
    private void sentOtp(String otp){
        PhoneAuthCredential authCredential= PhoneAuthProvider.getCredential(verificationId,otp);
        signInWithPhoneAuthCredential(authCredential);
    }
    private void sentOtpagain(){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phonenumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                            }
                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                super.onCodeSent(verificationId,token);
                                verificationId=verificationId;
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void verifyCode() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        InformationFragment addfragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("sdt", phonenumber);
        bundle.putInt("id", id);
        bundle.putString("verificationId", verificationId);
        addfragment.setArguments(bundle);
        ft.replace(R.id.nav_host_fragment_activity_main2, addfragment);
        ft.commit();
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener( getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            updateSDT(id, phonenumber);
                            verifyCode();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            }
                        }
                    }
                });
    }
    private void updateSDT(int ida, String sdt) {
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.updatesdt(ida, sdt);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }



}