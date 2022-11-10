package tv.fw.example.shopping.shoppingcart

import tv.fw.common.product.Product
import tv.fw.common.product.ProductUnit

/**
 * Dummy repository class that emulates adding items to the shopping cart
 */
object ShoppingCartRepository {
    private val products = mutableListOf<Product>()
    private val orders = mutableListOf<Order>()

    fun setProducts(products: List<Product>) {
        ShoppingCartRepository.products.clear()
        ShoppingCartRepository.products.addAll(products)
    }

    fun getOrders(): List<Order> {
        // makes copy of the list
        return orders.toMutableList().toList()
    }

    fun add(productId: String, unitId: String) {
        val product = products.firstOrNull { it.id == productId } ?: return
        val unit = product.units.firstOrNull { it.id == unitId } ?: return
        orders.add(Order(product = product, unit = unit))
    }

    fun clearOrders() {
        orders.clear()
    }

    data class Order(
        val product: Product?,
        val unit: ProductUnit?,
    )
}
