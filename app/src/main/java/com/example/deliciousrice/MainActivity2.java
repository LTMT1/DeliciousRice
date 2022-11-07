package com.example.deliciousrice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Customer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.deliciousrice.databinding.ActivityMain2Binding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    private String email = "", password = "";
    private String image, user_name, phone_number, address, birthday;
    private int id_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.shop, R.id.explore, R.id.cart, R.id.favorite, R.id.account)
                .build();
        NavigationUI.setupWithNavController(binding.navView, navController);
        navView.setItemIconTintList(null);
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
                image = customer.getEmail();
                birthday = customer.getBirthday();
                phone_number = customer.getPhone_number();
                address = customer.getAddress();
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
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
}