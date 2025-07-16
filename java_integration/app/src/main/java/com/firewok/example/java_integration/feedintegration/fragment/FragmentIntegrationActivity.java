package com.firewok.example.java_integration.feedintegration.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.firewok.example.java_integration.R;
import com.firewok.example.java_integration.databinding.ActivityFragmentIntegrationBinding;

public class FragmentIntegrationActivity extends AppCompatActivity {
    private ActivityFragmentIntegrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFragmentIntegrationBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.fragment_screen_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, FeedViewFragment.newInstance(), null)
                    .commit();
        }
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
        return new Intent(context, FragmentIntegrationActivity.class);
    }
} 