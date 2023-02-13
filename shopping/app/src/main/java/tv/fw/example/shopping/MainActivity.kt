package tv.fw.example.shopping

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.firework.common.feed.FeedResource
import com.firework.common.product.CurrencyCode
import com.firework.common.product.Product
import com.firework.sdk.FireworkSdk
import com.firework.shopping.EmbeddedCartFactory
import com.firework.shopping.ProductHydrator
import com.firework.shopping.Shopping
import com.firework.videofeed.FwVideoFeedView
import com.firework.videofeed.baseOptions
import com.firework.videofeed.viewOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tv.fw.example.shopping.BuildConfig.FW_CHANNEL_ID
import tv.fw.example.shopping.BuildConfig.FW_PLAYLIST_ID
import tv.fw.example.shopping.databinding.ActivityMainBinding
import tv.fw.example.shopping.shoppingcart.ShoppingActivity
import tv.fw.example.shopping.shoppingcart.ShoppingCartFragment
import tv.fw.example.shopping.shoppingcart.ShoppingCartRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val videoFeedView: FwVideoFeedView
        get() = binding.videoFeedView

    private val uiScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val checkoutFragmentFactory = object : EmbeddedCartFactory {
        override fun getInstance(): Fragment {
            return ShoppingCartFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setupDetails()

        initVideoFeedView()

        setupShopping()
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetails() {
        binding.details.source.text = "Playlist"
        binding.details.channel.text = FW_CHANNEL_ID
        binding.details.playlist.text = FW_PLAYLIST_ID
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

    private fun setupShopping() {
        FireworkSdk.shopping.setEmbeddedCartFactory(checkoutFragmentFactory)
        FireworkSdk.shopping.setShoppingCartBehaviour(Shopping.CartBehaviour.Embedded("some title"))
        FireworkSdk.shopping.setOnCartActionListener(
            object : Shopping.OnCartActionListener {
                override fun onProductAddedToCart(productId: String, unitId: String) {
                    uiScope.launch {
                        FireworkSdk.shopping.setAddToCartStatus(Shopping.AddToCartStatus.Loading)
                        delay(LONG_OPERATION_DELAY)
                        val status = Shopping.AddToCartStatus.Success(
                            productId = productId,
                            unitId = unitId,
                        )
                        FireworkSdk.shopping.setAddToCartStatus(status)
                        ShoppingCartRepository.add(productId, unitId)
                        Toast.makeText(this@MainActivity, "Added to cart", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onCartClicked() {
                    ShoppingActivity.start(this@MainActivity)
                }
            },
        )
        FireworkSdk.shopping.setOnProductActionListener(
            object : Shopping.OnProductActionListener {

                override fun onProductHydration(products: List<Product>, hydrator: ProductHydrator) {
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

                override fun onDisplayProductInfo(productId: String, unitId: String, productWebUrl: String?): Boolean {
                    Toast.makeText(
                        this@MainActivity,
                        "Host App: Product Url: $productWebUrl",
                        Toast.LENGTH_SHORT,
                    ).show()
                    return true
                }
            },
        )
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

    companion object {
        private const val LONG_OPERATION_DELAY = 2000L
    }
}
