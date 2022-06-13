package tv.fw.example.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tv.fw.common.video.Product
import tv.fw.example.shopping.databinding.ActivityMainBinding
import tv.fw.example.shopping.shoppingcart.ShoppingActivity
import tv.fw.example.shopping.shoppingcart.ShoppingCartFragment
import tv.fw.example.shopping.shoppingcart.ShoppingCartRepository
import tv.fw.fireworksdk.FireworkSdk
import tv.fw.shopping.EmbeddedCartFactory
import tv.fw.shopping.ProductHydrator
import tv.fw.shopping.Shopping

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
        val carouselView = binding.carouselView
        carouselView.init()
        setupShopping()
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
                        val status = Shopping.AddToCartStatus.Success(productId = productId,
                            unitId = unitId,
                            numberOfItemsInCart = 1)
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
                override fun onProductHydration(
                    products: List<Product>,
                    hydrator: ProductHydrator,
                ) {
                    ShoppingCartRepository.setProducts(products)
                    uiScope.launch {
                        delay(LONG_OPERATION_DELAY)
                        products.forEach { product ->
                            hydrateProduct(product, hydrator)
                        }
                        val hydratedProducts = hydrator.completeHydration()
                        ShoppingCartRepository.setProducts(hydratedProducts)
                    }
                }

                override fun onDisplayProductInfo(
                    productId: String,
                    unitId: String,
                    productWebUrl: String?,
                ): Boolean {
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

    private fun hydrateProduct(product: Product, hydrator: ProductHydrator) {
        product.id?.let { productId ->
            hydrator.hydrate(productId) {
                name(name = "new product name")
                description("This is modified product description from the host app.")
                isAvailable(isAvailable = true)
                val attributes = product.attributeNames.toMutableList()
                attributes.add("Box color")
                product.units?.forEach { unit ->
                    unit.id?.let { variantId ->
                        variant(variantId) {
                            currency("EUR")
                            price(price = 23.04)
                            name(name = "New variant")
                            val hydratedOptions = mutableMapOf<String?, String?>()
                            hydratedOptions["Box color"] = "Red"
                            hydratedOptions.putAll(unit.options)
                            options(options = hydratedOptions)
                        }
                    }
                }
                attributes(attributes)
            }
        }
    }

    companion object {
        private const val LONG_OPERATION_DELAY = 2000L
    }
}