package com.example.deliciousrice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.ui.shop.ShopFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.deliciousrice.databinding.ActivityMain2Binding;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    private static ActivityMain2Binding binding;
    private String email = "", password = "";
    private String image, user_name, phone_number, address, birthday, emaill, passs;
    private int id_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                String token = task.getResult().getToken();
                Log.e("sssssssssss", token);
                registerToken(token);
            }
        });
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.shop, R.id.explore, R.id.cart, R.id.favorite, R.id.account)
                .build();
        NavigationUI.setupWithNavController(binding.navView, navController);
        navView.setItemIconTintList(null);
        binding.navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.shop:
                        navController.navigate(R.id.shop);
                        break;
                    case R.id.explore:
                        navController.navigate(R.id.explore);
                        break;
                    case R.id.cart:
                        navController.navigate(R.id.cart);
                        break;
                    case R.id.favorite:
                        navController.navigate(R.id.favorite);
                        break;
                    case R.id.account:
                        navController.navigate(R.id.account);
                        break;
                }
                return true;
            }
        });



        setStatusBarColor();
        getDatas();
        getdataCustomer(email, password);
    }

    private void setStatusBarColor() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorTaskBar));
    }

    private void getDatas() {
        SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
        email = preferences.getString("gmail", "");
        password = preferences.getString("matkhau", "");
    }

    public void getdataCustomer(String emails, String pass) {
        ApiProduct apiProduct = ApiService.getService();
        Call<List<Customer>> callback = apiProduct.getcustomer(emails, pass);
        callback.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                ArrayList<Customer> mangyeuthich = (ArrayList<Customer>) response.body();
                Customer customer = mangyeuthich.get(0);
                id_customer = customer.getId_customer();
                user_name = customer.getUser_name();
                image = customer.getImage();
                birthday = customer.getBirthday();
                phone_number = customer.getPhone_number();
                address = customer.getAddress();
                emaill = customer.getEmail();
                passs = customer.getPassword();
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }

    public String getEmaill() {
        return emaill;
    }

    public String getPasss() {
        return passs;
    }

    public void updateMain() {
        getdataCustomer(email, password);
    }

    public int getId_customer() {
        return id_customer;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getImage() {
        return image;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getAddress() {
        return address;
    }

    private void registerToken(String token) {
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.token(token);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
    }
    public static void setBugdeNumber() {
        int number = ShopFragment.Cartlist.size();
        if (number > 0){
            BadgeDrawable badgeDrawable = binding.navView.getOrCreateBadge(R.id.cart);
        badgeDrawable.setMaxCharacterCount(3);
        badgeDrawable.setNumber(number);
        badgeDrawable.setVisible(true);
    }
    }
}