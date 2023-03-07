package com.firework.example.feedintegration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firework.example.feedintegration.activity.ActivityIntegrationActivity
import com.firework.example.feedintegration.addremove.AddRemoveIntegrationActivity
import com.firework.example.feedintegration.databinding.ActivityMainBinding
import com.firework.example.feedintegration.fragment.FragmentIntegrationActivity
import com.firework.example.feedintegration.recyclerview.RecyclerViewIntegrationActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.activityFeed.setOnClickListener {
            startActivity(ActivityIntegrationActivity.intent(this))
        }
        binding.fragmentFeed.setOnClickListener {
            startActivity(FragmentIntegrationActivity.intent(this))
        }
        binding.recyclerViewFeed.setOnClickListener {
            startActivity(RecyclerViewIntegrationActivity.intent(this))
        }
        binding.addRemoveFeed.setOnClickListener {
            startActivity(AddRemoveIntegrationActivity.intent(this))
        }
    }
}
