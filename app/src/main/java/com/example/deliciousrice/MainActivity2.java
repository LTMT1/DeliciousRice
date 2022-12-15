package com.example.deliciousrice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.deliciousrice.Activity.HelloScreenActivity;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Cart;
import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.databinding.ActivityMain2Binding;
import com.example.deliciousrice.ui.cart.DaoCart;
import com.example.deliciousrice.ui.shop.ShopFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    private static ActivityMain2Binding binding;
    private String email = "", password = "";
    private String image, id_application, user_name, address, birthday, emaill, passs;
    public static String phone_number;
    private int id_customer;
    private long backPressTime;
    private Toast mToast;
    public static DaoCart daoCart;
    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        daoCart = new DaoCart(getApplicationContext());
        getToken();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.shop, R.id.explore, R.id.cart, R.id.favorite, R.id.account)
                .build();
        NavigationUI.setupWithNavController(binding.navView, navController);
        navView.setItemIconTintList(null);
        setBugdeNumber();
        setStatusBarColor();
        getDatas();
        getdataCustomer(email);
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

    public void getdataCustomer(String emails) {
        try {
            ApiProduct apiProduct = ApiService.getService();
            Call<List<Customer>> callback = apiProduct.getcustomer(emails);
            callback.enqueue(new Callback<List<Customer>>() {
                @Override
                public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                    ArrayList<Customer> mangyeuthich = (ArrayList<Customer>) response.body();
                    Customer customer = mangyeuthich.get(0);
                    id_customer = customer.getId_customer();
                    id_application = customer.getId_application();
                    user_name = customer.getUser_name();
                    image = customer.getImage();
                    birthday = customer.getBirthday();
                    phone_number = customer.getPhone_number();
                    address = customer.getAddress();
                    emaill = customer.getEmail();
                    passs = customer.getPassword();
                    BadgeDrawable badgeDrawable = binding.navView.getOrCreateBadge(R.id.account);
                    if (phone_number.equals("")) {
                        badgeDrawable.setVisible(true);
//                            badgeDrawable.setVerticalOffset(dpToPx(MainActivity2.this,3));
                        badgeDrawable.setBadgeTextColor(getResources().getColor(R.color.colorTaskBar));
                    } else {
                        badgeDrawable.setVisible(false);
                    }
                }

                @Override
                public void onFailure(Call<List<Customer>> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }

    public String getId_application() {
        return id_application;
    }

    public String getEmaill() {
        return emaill;
    }

    public String getPasss() {
        return passs;
    }

    public void updateMain() {
        getdataCustomer(email);
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
        binding = null;
    }

    public static void setBugdeNumber() {
        ShopFragment.Cartlist = (ArrayList<Cart>) daoCart.getall();
        int number = ShopFragment.Cartlist.size();
        BadgeDrawable badgeDrawable = binding.navView.getOrCreateBadge(R.id.cart);
        badgeDrawable.setMaxCharacterCount(3);
        badgeDrawable.setNumber(number);
        if (number > 0) {
            badgeDrawable.setVisible(true);
        } else {
            badgeDrawable.setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            mToast.cancel();

            Intent intent = new Intent(getApplicationContext(), HelloScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            finish();
            System.exit(0);

        } else {
            mToast = Toast.makeText(MainActivity2.this, "Ấn lần nữa để thoát", Toast.LENGTH_SHORT);
            mToast.show();
        }
        backPressTime = System.currentTimeMillis();
    }

    public void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                token = task.getResult().getToken();
                registerToken(token);
            }
        });
        FirebaseMessaging.getInstance().subscribeToTopic("test");
    }
}