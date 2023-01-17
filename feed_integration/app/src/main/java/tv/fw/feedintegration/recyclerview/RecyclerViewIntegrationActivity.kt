package tv.fw.feedintegration.recyclerview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import tv.fw.feedintegration.R
import tv.fw.feedintegration.databinding.ActivityRecyclerViewBinding

class RecyclerViewIntegrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setTitle(R.string.recycler_view_screen_title)
    }

    companion object {
        fun intent(context: Context) = Intent(context, RecyclerViewIntegrationActivity::class.java)
    }
}
