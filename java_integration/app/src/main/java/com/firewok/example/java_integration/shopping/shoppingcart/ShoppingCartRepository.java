package com.firewok.example.java_integration.shopping.shoppingcart;

import com.firework.common.product.Product;
import com.firework.common.product.ProductUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Dummy repository class that emulates adding items to the shopping cart
 */
public class ShoppingCartRepository {
    private static final ShoppingCartRepository INSTANCE = new ShoppingCartRepository();
    
    private final List<Product> products = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();

    private ShoppingCartRepository() {
        // Private constructor to prevent instantiation
    }

    public static ShoppingCartRepository getInstance() {
        return INSTANCE;
    }

    public static void setProducts(List<Product> products) {
        getInstance().setProductsInternal(products);
    }

    public static List<Order> getOrders() {
        return getInstance().getOrdersInternal();
    }

    public static void add(String productId, String unitId) {
        getInstance().addInternal(productId, unitId);
    }

    public static void clearOrders() {
        getInstance().clearOrdersInternal();
    }

    private void setProductsInternal(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
    }

    private List<Order> getOrdersInternal() {
        // makes copy of the list
        return new ArrayList<>(orders);
    }

    private void addInternal(String productId, String unitId) {
        Product product = null;
        for (Product p : products) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }
        if (product == null) return;

        ProductUnit unit = null;
        for (ProductUnit u : product.getUnits()) {
            if (u.getId().equals(unitId)) {
                unit = u;
                break;
            }
        }
        if (unit == null) return;

        orders.add(new Order(product, unit));
    }

    private void clearOrdersInternal() {
        orders.clear();
    }

    public static class Order {
        private final Product product;
        private final ProductUnit unit;

        public Order(Product product, ProductUnit unit) {
            this.product = product;
            this.unit = unit;
        }

        public Product getProduct() {
            return product;
        }

        public ProductUnit getUnit() {
            return unit;
        }
    }
} 