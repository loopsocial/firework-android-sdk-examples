package tv.fw.feedintegration.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import tv.fw.feedintegration.R
import tv.fw.feedintegration.databinding.ActivityFragmentIntegrationBinding

class FragmentIntegrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFragmentIntegrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentIntegrationBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.fragment_screen_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, FeedViewFragment.newInstance(), null)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun intent(context: Context) = Intent(context, FragmentIntegrationActivity::class.java)
    }
}
