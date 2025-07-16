package com.firewok.example.java_integration.shopping;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.firework.analyticsevents.VideoInfo;
import com.firework.common.feed.FeedResource;
import com.firework.common.product.CurrencyCode;
import com.firework.common.product.Product;
import com.firework.sdk.FireworkSdk;
import com.firework.shopping.EmbeddedCartFactory;
import com.firework.shopping.LinkButtonOptions;
import com.firework.shopping.ProductCardsOptions;
import com.firework.shopping.ProductDetailsOptions;
import com.firework.shopping.ProductHydrator;
import com.firework.shopping.Shopping;
import com.firework.shopping.ShoppingCtaButtonOptions;
import com.firework.shopping.ShoppingTheme;
import com.firework.shopping.ShoppingViewOptions;
import com.firework.videofeed.FwVideoFeedView;
import com.firework.viewoptions.BaseOption;
import com.firework.viewoptions.ViewOptions;
import com.firewok.example.java_integration.BuildConfig;
import com.firewok.example.java_integration.R;
import com.firewok.example.java_integration.databinding.ActivityAddToCartModeBinding;
import com.firewok.example.java_integration.shopping.shoppingcart.ShoppingActivity;
import com.firewok.example.java_integration.shopping.shoppingcart.ShoppingCartFragment;
import com.firewok.example.java_integration.shopping.shoppingcart.ShoppingCartRepository;
import java.util.List;

public class AddToCartModeActivity extends AppCompatActivity {
    private static final long LONG_OPERATION_DELAY = 2000L;
    private static final boolean ALLOW_PRODUCTS_HYDRATION = false;
    
    private ActivityAddToCartModeBinding binding;
    private Handler mainHandler;

    private final EmbeddedCartFactory checkoutFragmentFactory = new EmbeddedCartFactory() {
        @Override
        public Fragment getInstance() {
            return new ShoppingCartFragment();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddToCartModeBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        
        mainHandler = new Handler(Looper.getMainLooper());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.add_to_cart_mode_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        initVideoFeedView();
        setupDetails();
        setupShopping();
    }

    private void initVideoFeedView() {
        FwVideoFeedView videoFeedView = binding.videoFeedView;
        FeedResource playlistFeedResource = new FeedResource.Playlist(BuildConfig.FW_CHANNEL_ID, BuildConfig.FW_PLAYLIST_ID);

        ViewOptions viewOptions = new ViewOptions.Builder()
                .baseOption(new BaseOption.Builder()
                        .feedResource(playlistFeedResource)
                        .build())
                .build();
        
        videoFeedView.init(viewOptions);
    }

    @SuppressLint("SetTextI18n")
    private void setupDetails() {
        binding.details.source.setText("Playlist");
        binding.details.channel.setText(BuildConfig.FW_CHANNEL_ID);
        binding.details.playlist.setText(BuildConfig.FW_PLAYLIST_ID);
    }

    private void setupShopping() {
        Shopping shopping = FireworkSdk.INSTANCE.getShopping();
        shopping.setEmbeddedCartFactory(checkoutFragmentFactory);
        ProductCardsOptions productCardsOptions = new ProductCardsOptions.Default();
        ShoppingViewOptions shoppingViewOptions = new ShoppingViewOptions(
                ShoppingTheme.DARK,
                productCardsOptions, // ProductCardsOptions - trying null
                new ProductDetailsOptions(
                        new LinkButtonOptions(true),
                        new ShoppingCtaButtonOptions(ShoppingCtaButtonOptions.Text.ADD_TO_CART, 0)
                )
        );
        shopping.setShoppingViewOptions(shoppingViewOptions);
        shopping.setShoppingCartBehaviour(new Shopping.CartBehaviour.Embedded("some title"));
        
        shopping.setOnCtaButtonClicked((productId, unitId, productWebUrl, product, unit) -> {
            FireworkSdk.INSTANCE.getShopping().setCtaButtonStatus(Shopping.CtaButtonStatus.Loading.INSTANCE);
            mainHandler.postDelayed(() -> {
                FireworkSdk.INSTANCE.getShopping().setCtaButtonStatus(Shopping.CtaButtonStatus.Success.INSTANCE);
                ShoppingCartRepository.add(productId, unitId);
                Toast.makeText(AddToCartModeActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
            }, LONG_OPERATION_DELAY);
        });

        shopping.setOnCartClickListener(new Shopping.OnCartClickListener() {
            @Override
            public void onCartClick(@NonNull VideoInfo videoInfo) {
                ShoppingActivity.start(AddToCartModeActivity.this);
            }
        });

        // Note: setOnCartClickListener removed due to compilation issues

        shopping.setOnProductHydrationListener((products, hydrator, unit) -> {
            if (!ALLOW_PRODUCTS_HYDRATION) {
                return;
            }
            ShoppingCartRepository.setProducts(products);
            mainHandler.postDelayed(() -> {
                for (int i = 0; i < products.size(); i++) {
                    hydrateProduct(products.get(i), hydrator, i);
                }
                List<Product> hydratedProducts = hydrator.completeHydration();
                ShoppingCartRepository.getInstance().setProducts(hydratedProducts);
            }, LONG_OPERATION_DELAY);
        });
        
        shopping.setOnProductLinkClickListener((productId, unitId, productWebUrl, product, unit) -> {
            Toast.makeText(
                    AddToCartModeActivity.this,
                    "Host App: Product Url: " + productWebUrl,
                    Toast.LENGTH_SHORT
            ).show();
            return true;
        });
    }

    private void hydrateProduct(Product product, ProductHydrator hydrator, int position) {
        String productId = product.getId();
        if (productId != null) {
            hydrator.hydrate(productId, builder -> {
                builder.name("new product name");
                builder.description("This is modified product description from the host app.");
                
                for (com.firework.common.product.ProductUnit unit : product.getUnits()) {
                    String variantId = unit.getId();
                    if (variantId != null) {
                        builder.variant(variantId, variantBuilder -> {
                            variantBuilder.currency(CurrencyCode.EUR);
                            variantBuilder.price(23.04);
                            variantBuilder.name("New variant");
                            return null;
                        });
                    }
                }
                builder.isAvailable(position == 2);
                return null;
            });
        }
    }

    @Override
    protected void onDestroy() {
        binding.videoFeedView.destroy();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, AddToCartModeActivity.class);
    }
} 