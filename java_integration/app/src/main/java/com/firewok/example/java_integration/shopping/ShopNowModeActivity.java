package com.firewok.example.java_integration.shopping;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.firework.common.feed.FeedResource;
import com.firework.common.product.CurrencyCode;
import com.firework.common.product.Product;
import com.firework.error.shopping.ShoppingError;
import com.firework.sdk.FireworkSdk;
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
import com.firewok.example.java_integration.databinding.ActivityShopNowModeBinding;
import com.firewok.example.java_integration.shopping.shoppingcart.ShoppingCartRepository;
import java.util.List;

public class ShopNowModeActivity extends AppCompatActivity implements Shopping.OnShoppingErrorListener {
    private static final String TAG = ShopNowModeActivity.class.getSimpleName();
    private static final boolean ALLOW_PRODUCTS_HYDRATION = false;
    private static final long LONG_OPERATION_DELAY = 2000L;
    
    private ActivityShopNowModeBinding binding;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopNowModeBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        
        mainHandler = new Handler(Looper.getMainLooper());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.shop_now_mode_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        initVideoFeedView();
        setupDetails();
        setupCtaModeShopping();
    }

    private void initVideoFeedView() {
        FeedResource playlistFeedResource = new FeedResource.Playlist(BuildConfig.FW_CHANNEL_ID, BuildConfig.FW_PLAYLIST_ID);

        ViewOptions viewOptions = new ViewOptions.Builder()
                .baseOption(new BaseOption.Builder()
                        .feedResource(playlistFeedResource)
                        .build())
                .build();

        binding.videoFeedView.init(viewOptions);
    }

    @SuppressLint("SetTextI18n")
    private void setupDetails() {
        binding.details.source.setText("Playlist");
        binding.details.channel.setText(BuildConfig.FW_CHANNEL_ID);
        binding.details.playlist.setText(BuildConfig.FW_PLAYLIST_ID);
    }

    private void setupCtaModeShopping() {
        Shopping shopping = FireworkSdk.INSTANCE.getShopping();
        shopping.setOnShoppingErrorListener(this);
        shopping.setShoppingCartBehaviour(Shopping.CartBehaviour.NoCart.INSTANCE);
        ProductCardsOptions productCardsOptions = new ProductCardsOptions.Default();
        ShoppingViewOptions shoppingViewOptions = new ShoppingViewOptions(
                ShoppingTheme.DARK,
                productCardsOptions,
                new ProductDetailsOptions(
                        new LinkButtonOptions(false),

                        new ShoppingCtaButtonOptions(ShoppingCtaButtonOptions.Text.SHOP_NOW, 0)
                )
        );
        shopping.setShoppingViewOptions(shoppingViewOptions);
        
        shopping.setOnCtaButtonClicked((productId, unitId, productWebUrl, product, unit) -> {
            Uri webpage = Uri.parse(productWebUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            FireworkSdk.INSTANCE.enterPip(
                    isIgnored -> null, // onSuccess
                    pipEnterError -> null // onError
            );
        });
        
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
    public void onShoppingError(ShoppingError error) {
        Log.e(TAG, "Shopping error: " + error);
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
        return new Intent(context, ShopNowModeActivity.class);
    }
} 