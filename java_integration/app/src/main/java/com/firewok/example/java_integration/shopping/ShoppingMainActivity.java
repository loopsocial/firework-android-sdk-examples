package com.firewok.example.java_integration.shopping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.firewok.example.java_integration.R;
import com.firewok.example.java_integration.databinding.ActivityShoppingMainBinding;

public class ShoppingMainActivity extends AppCompatActivity {
    private ActivityShoppingMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.shopping_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        binding.btnAddToCart.setOnClickListener(v -> {
            startActivity(AddToCartModeActivity.getIntent(this));
        });
        
        binding.btnShopNow.setOnClickListener(v -> {
            startActivity(ShopNowModeActivity.getIntent(this));
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, ShoppingMainActivity.class);
    }
} 