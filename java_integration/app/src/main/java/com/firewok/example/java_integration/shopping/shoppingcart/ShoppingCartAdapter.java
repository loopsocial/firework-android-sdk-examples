package com.firewok.example.java_integration.shopping.shoppingcart;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firework.imageloading.ImageLoader;
import com.firewok.example.java_integration.databinding.ProductUnitItemBinding;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    private final ImageLoader imageLoader;
    private final List<ProductUnitItem> items = new ArrayList<>();

    public ShoppingCartAdapter(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ProductUnitItem> data) {
        items.clear();
        items.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ProductUnitItemBinding binding = ProductUnitItemBinding.inflate(inflater, parent, false);
        return new HeaderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public abstract static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void bind(ProductUnitItem item);
    }

    private class HeaderViewHolder extends ViewHolder {
        private final ProductUnitItemBinding binding;

        public HeaderViewHolder(ProductUnitItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bind(ProductUnitItem item) {
            binding.tvTitle.setText(item.getTitle());
            binding.tvSubTitle.setText(item.getSubTitle());
            if (item.getIconUrl() != null) {
                imageLoader.load(item.getIconUrl(), binding.ivProductIcon, null);
            }
        }
    }
} 