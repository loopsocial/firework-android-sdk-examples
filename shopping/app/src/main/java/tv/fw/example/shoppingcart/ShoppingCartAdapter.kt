package tv.fw.example.shoppingcart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tv.fw.example.databinding.ProductUnitItemBinding
import tv.fw.imageloading.ImageLoader

internal class ShoppingCartAdapter(
    private val imageLoader: ImageLoader,
) : RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {
    private val items = mutableListOf<ProductUnitItem>()

    fun setData(data: List<ProductUnitItem>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductUnitItemBinding.inflate(inflater, parent, false)
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: ProductUnitItem)
    }

    private inner class HeaderViewHolder(private val binding: ProductUnitItemBinding) :
        ViewHolder(binding.root) {
        override fun bind(item: ProductUnitItem) {
            with(binding) {
                tvTitle.text = item.title
                tvSubTitle.text = item.subTitle
                item.iconUrl?.let { url -> imageLoader.load(url, ivProductIcon) }
            }
        }
    }
}
