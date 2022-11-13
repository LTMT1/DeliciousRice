package com.example.deliciousrice.ui.account.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deliciousrice.R;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ImageView mDialButton = (ImageView) findViewById(R.id.imageView4);
        final TextView mPhoneNoEt = (TextView) findViewById(R.id.textView18);
        ImageView mDialButton2 = (ImageView) findViewById(R.id.imageView44);
        mDialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = mPhoneNoEt.getText().toString();
                if(!TextUtils.isEmpty(phoneNo)) {
                    String dial = "tel:" + phoneNo;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(ContactActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mDialButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(ContactActivity.this, EmailActivity.class);
                startActivity(mIntent);
            }
        });

    }


    }

