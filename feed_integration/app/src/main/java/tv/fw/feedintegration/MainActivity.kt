package tv.fw.feedintegration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tv.fw.feedintegration.activity.ActivityIntegrationActivity
import tv.fw.feedintegration.databinding.ActivityMainBinding
import tv.fw.feedintegration.fragment.FragmentIntegrationActivity
import tv.fw.feedintegration.recyclerview.RecyclerViewIntegrationActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnActivity.setOnClickListener {
            startActivity(ActivityIntegrationActivity.intent(this))
        }
        binding.btnFragment.setOnClickListener {
            startActivity(FragmentIntegrationActivity.intent(this))
        }
        binding.btnRecyclerView.setOnClickListener {
            startActivity(RecyclerViewIntegrationActivity.intent(this))
        }
    }
}
