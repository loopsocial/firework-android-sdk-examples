package com.firewok.example.java_integration.feedintegration.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.firewok.example.java_integration.R;
import com.firewok.example.java_integration.databinding.ActivityRecyclerViewIntegrationBinding;

public class RecyclerViewIntegrationActivity extends AppCompatActivity {
    private ActivityRecyclerViewIntegrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecyclerViewIntegrationBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.recycler_view_screen_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, FeedListFragment.newInstance(), null)
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
        return new Intent(context, RecyclerViewIntegrationActivity.class);
    }
} 