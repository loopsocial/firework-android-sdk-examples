package tv.fw.feedintegration.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import tv.fw.feedintegration.R
import tv.fw.feedintegration.databinding.ActivityFragmentIntegrationBinding

class FragmentIntegrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFragmentIntegrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentIntegrationBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setTitle(R.string.fragment_screen_title)
    }

    companion object {
        fun intent(context: Context) = Intent(context, FragmentIntegrationActivity::class.java)
    }
}
