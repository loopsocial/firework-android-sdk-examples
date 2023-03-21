package tv.fw.example.shopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tv.fw.example.shopping.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnAddToCart.setOnClickListener {
            startActivity(AddToCartModeActivity.getIntent(this))
        }
        binding.btnShopNow.setOnClickListener {
            startActivity(ShopNowModeActivity.getIntent(this))
        }
    }
}
