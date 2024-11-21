package com.firework.example.shopping

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.firework.common.feed.FeedResource
import com.firework.common.product.CurrencyCode
import com.firework.common.product.Product
import com.firework.example.shopping.BuildConfig.FW_CHANNEL_ID
import com.firework.example.shopping.BuildConfig.FW_PLAYLIST_ID
import com.firework.example.shopping.databinding.ActivityAddToCartModeBinding
import com.firework.example.shopping.shoppingcart.ShoppingActivity
import com.firework.example.shopping.shoppingcart.ShoppingCartFragment
import com.firework.example.shopping.shoppingcart.ShoppingCartRepository
import com.firework.sdk.FireworkSdk
import com.firework.shopping.EmbeddedCartFactory
import com.firework.shopping.LinkButtonOptions
import com.firework.shopping.ProductDetailsOptions
import com.firework.shopping.ProductHydrator
import com.firework.shopping.Shopping
import com.firework.shopping.ShoppingCtaButtonOptions
import com.firework.shopping.ShoppingTheme
import com.firework.shopping.ShoppingViewOptions
import com.firework.videofeed.FwVideoFeedView
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.viewOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddToCartModeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddToCartModeBinding

    private val videoFeedView: FwVideoFeedView
        get() = binding.videoFeedView

    private val uiScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val checkoutFragmentFactory =
        object : EmbeddedCartFactory {
            override fun getInstance(): Fragment {
                return ShoppingCartFragment()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddToCartModeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initVideoFeedView()

        setupDetails()

        setupShopping()
    }

    private fun initVideoFeedView() {
        val playlistFeedResource = FeedResource.Playlist(channelId = FW_CHANNEL_ID, playlistId = FW_PLAYLIST_ID)

        val viewOptions =
            viewOptions {
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

    private fun setupShopping() {
        val shopping = FireworkSdk.shopping
        shopping.setEmbeddedCartFactory(checkoutFragmentFactory)
        shopping.setShoppingViewOptions(
            ShoppingViewOptions(
                theme = ShoppingTheme.DARK,
                productDetailsOptions =
                    ProductDetailsOptions(
                        linkButtonOptions = LinkButtonOptions(true),
                        shoppingCtaButtonOptions = ShoppingCtaButtonOptions(text = ShoppingCtaButtonOptions.Text.ADD_TO_CART),
                    ),
            ),
        )
        shopping.setShoppingCartBehaviour(Shopping.CartBehaviour.Embedded("some title"))
        shopping.setOnCtaButtonClicked { productId, unitId, _, _, _ ->
            uiScope.launch {
                FireworkSdk.shopping.setCtaButtonStatus(Shopping.CtaButtonStatus.Loading)
                delay(LONG_OPERATION_DELAY)
                FireworkSdk.shopping.setCtaButtonStatus(Shopping.CtaButtonStatus.Success)
                ShoppingCartRepository.add(productId, unitId)
                Toast.makeText(this@AddToCartModeActivity, "Added to cart", Toast.LENGTH_SHORT).show()
            }
        }
        shopping.setOnCartClickListener {
            ShoppingActivity.start(this@AddToCartModeActivity)
        }

        shopping.setOnProductHydrationListener { products: List<Product>, hydrator: ProductHydrator, _ ->
            if (!ALLOW_PRODUCTS_HYDRATION) {
                return@setOnProductHydrationListener
            }
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
        shopping.setOnProductLinkClickListener { _, _, productWebUrl, _, _ ->
            Toast.makeText(
                this@AddToCartModeActivity,
                "Host App: Product Url: $productWebUrl",
                Toast.LENGTH_SHORT,
            ).show()
            return@setOnProductLinkClickListener true
        }
    }

    private fun hydrateProduct(
        product: Product,
        hydrator: ProductHydrator,
        position: Int,
    ) {
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

    companion object {
        private const val LONG_OPERATION_DELAY = 2000L
        private const val ALLOW_PRODUCTS_HYDRATION = false

        fun getIntent(context: Context): Intent {
            return Intent(context, AddToCartModeActivity::class.java)
        }
    }
}
