package com.firework.example.shopping.shoppingcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.firework.example.shopping.databinding.FragmentCheckoutBinding
import com.firework.imageloading.glide.GlideImageLoaderFactory
import com.firework.sdk.FireworkSdk

class ShoppingCartFragment : Fragment() {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding: FragmentCheckoutBinding get() = _binding!!

    private val imageLoader by lazy {
        GlideImageLoaderFactory.createInstance(context = requireContext())
    }

    private val checkoutAdapter by lazy {
        ShoppingCartAdapter(imageLoader)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvList.adapter = checkoutAdapter
        binding.checkoutBtn.setOnClickListener {
            ShoppingCartRepository.clearOrders()
            FireworkSdk.shopping.numberOfItemsInCart = 0
            FireworkSdk.shopping.dismiss()
            (activity as? ShoppingCartListener)?.onCheckout()
            Toast.makeText(requireContext(), "Item purchased", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        checkoutAdapter.setData(
            ShoppingCartRepository.getOrders().map { order ->
                ProductUnitItem(
                    title = order.product?.name ?: "",
                    subTitle = "${order.unit?.price?.amount} ${order.unit?.price?.currencyCode}",
                    iconUrl = order.unit?.image?.url ?: "",
                )
            },
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
