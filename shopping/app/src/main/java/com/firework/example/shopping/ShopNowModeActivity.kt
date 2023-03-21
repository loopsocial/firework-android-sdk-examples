package com.firework.example.shopping

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.firework.common.feed.FeedResource
import com.firework.common.product.CurrencyCode
import com.firework.common.product.Product
import com.firework.error.shopping.ShoppingError
import com.firework.example.shopping.BuildConfig.FW_CHANNEL_ID
import com.firework.example.shopping.BuildConfig.FW_PLAYLIST_ID
import com.firework.example.shopping.databinding.ActivityShopNowModeBinding
import com.firework.example.shopping.shoppingcart.ShoppingCartRepository
import com.firework.sdk.FireworkSdk
import com.firework.shopping.LinkButtonOptions
import com.firework.shopping.ProductDetailsOptions
import com.firework.shopping.ProductHydrator
import com.firework.shopping.Shopping
import com.firework.shopping.ShoppingCtaButtonOptions
import com.firework.shopping.ShoppingViewOptions
import com.firework.videofeed.FwVideoFeedView
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.viewOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ShopNowModeActivity : AppCompatActivity(), Shopping.OnShoppingErrorListener {
    private lateinit var binding: ActivityShopNowModeBinding

    private val videoFeedView: FwVideoFeedView
        get() = binding.videoFeedView

    private val uiScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopNowModeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initVideoFeedView()

        setupDetails()

        setupCtaModeShopping()
    }

    private fun initVideoFeedView() {
        val playlistFeedResource = FeedResource.Playlist(channelId = FW_CHANNEL_ID, playlistId = FW_PLAYLIST_ID)

        val viewOptions = viewOptions {
            baseOptions {
                feedResource(playlistFeedResource)
            }
        }

        videoFeedView.init(viewOptions)
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetails() {
        binding.details.source.text = "Playlist"
        binding.details.channel.text = FW_CHANNEL_ID
        binding.details.playlist.text = FW_PLAYLIST_ID
    }

    private fun setupCtaModeShopping() {
        val shopping = FireworkSdk.shopping
        shopping.setOnShoppingErrorListener(this@ShopNowModeActivity)
        shopping.setShoppingCartBehaviour(Shopping.CartBehaviour.NoCart)
        shopping.setShoppingViewOptions(
            ShoppingViewOptions(
                ProductDetailsOptions(
                    linkButtonOptions = LinkButtonOptions(false),
                    shoppingCtaButtonOptions = ShoppingCtaButtonOptions(text = ShoppingCtaButtonOptions.Text.SHOP_NOW),
                ),
            ),
        )
        shopping.setOnCtaButtonClicked { _, _, productWebUrl ->
            val webpage: Uri = Uri.parse(productWebUrl)
            val intent = Intent(Intent.ACTION_VIEW, webpage).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            FireworkSdk.enterPip()
        }
        shopping.setOnProductHydrationListener { products, hydrator ->
            ShoppingCartRepository.setProducts(products)
            uiScope.launch {
                delay(LONG_OPERATION_DELAY)
                products.forEachIndexed { i, product ->
                    hydrateProduct(product, hydrator, i)
                }
                val hydratedProducts = hydrator.completeHydration()
                ShoppingCartRepository.setProducts(hydratedProducts)
            }
        }
    }

    private fun hydrateProduct(product: Product, hydrator: ProductHydrator, position: Int) {
        product.id?.let { id ->
            hydrator.hydrate(id) {
                name("new product name")
                description("This is modified product description from the host app.")
                product.units.forEach { unit ->
                    unit.id?.let { variantId ->
                        variant(variantId) {
                            currency(CurrencyCode.EUR)
                            price(price = 23.04)
                            name("New variant")
                        }
                    }
                }
                isAvailable(position == 2)
            }
        }
    }

    override fun onShoppingError(error: ShoppingError) {
        Log.e(TAG, "Shopping error: $error")
    }

    companion object {
        private val TAG = ShopNowModeActivity::class.java.simpleName
        private const val LONG_OPERATION_DELAY = 2000L
        fun getIntent(context: Context): Intent {
            return Intent(context, ShopNowModeActivity::class.java)
        }
    }
}
