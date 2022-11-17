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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.AccountFragment;
import com.example.deliciousrice.ui.explore.ExploreFragment;

public class ContactActivity extends AppCompatActivity {
    ImageView mDialButton,imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mDialButton = (ImageView) findViewById(R.id.imageView4);
        imgback=findViewById(R.id.img_back_Contact);
       /* imgback.setOnClickListener(v->{
            Fragment fragment=new ExploreFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Contact,fragment).commit();
        });*/




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

