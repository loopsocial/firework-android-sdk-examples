package com.firewok.example.java_integration.shopping.shoppingcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.firework.imageloading.ImageLoader;
import com.firework.imageloading.glide.GlideImageLoaderFactory;
import com.firework.sdk.FireworkSdk;
import com.firewok.example.java_integration.databinding.FragmentCheckoutBinding;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends Fragment {
    private FragmentCheckoutBinding binding;
    private ImageLoader imageLoader;
    private ShoppingCartAdapter checkoutAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        imageLoader = GlideImageLoaderFactory.INSTANCE.createInstance(requireContext());
        checkoutAdapter = new ShoppingCartAdapter(imageLoader);
        
        binding.rvList.setAdapter(checkoutAdapter);
        binding.checkoutBtn.setOnClickListener(v -> {
            ShoppingCartRepository.clearOrders();
            FireworkSdk.INSTANCE.getShopping().setNumberOfItemsInCart(0);
            FireworkSdk.INSTANCE.getShopping().dismiss();
            
            if (getActivity() instanceof ShoppingCartListener) {
                ((ShoppingCartListener) getActivity()).onCheckout();
            }
            
            Toast.makeText(requireContext(), "Item purchased", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        
        List<ShoppingCartRepository.Order> orders = ShoppingCartRepository.getInstance().getOrders();
        List<ProductUnitItem> items = new ArrayList<>();
        
        for (ShoppingCartRepository.Order order : orders) {
            String title = order.getProduct() != null ? order.getProduct().getName() : "";
            String subTitle = "";
            String iconUrl = "";
            
            if (order.getUnit() != null) {
                if (order.getUnit().getPrice() != null) {
                    subTitle = order.getUnit().getPrice().getAmount() + " " + order.getUnit().getPrice().getCurrencyCode();
                }
                if (order.getUnit().getImage() != null) {
                    iconUrl = order.getUnit().getImage().getUrl();
                }
            }
            
            items.add(new ProductUnitItem(title, subTitle, iconUrl));
        }
        
        checkoutAdapter.setData(items);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
} 