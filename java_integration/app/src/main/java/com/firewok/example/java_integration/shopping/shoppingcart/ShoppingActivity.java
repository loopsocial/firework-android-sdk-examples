package com.firewok.example.java_integration.shopping.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.firewok.example.java_integration.R;

public class ShoppingActivity extends AppCompatActivity implements ShoppingCartListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        
        if (savedInstanceState == null) {
            loadFragment(new ShoppingCartFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, null)
                .commit();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ShoppingActivity.class));
    }

    @Override
    public void onCheckout() {
        finish();
    }
} 