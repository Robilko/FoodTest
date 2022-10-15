package com.example.foodtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodtest.R
import com.example.foodtest.databinding.ProductCardRecyclerItemBinding
import com.example.foodtest.ui.adapter.listeners.RecyclerProductItemListener
import com.example.foodtest.ui.model.ProductItem

class ProductItemListAdapter(private val listener: RecyclerProductItemListener) :
    RecyclerView.Adapter<ProductItemListAdapter.ProductItemViewHolder>() {

    private val productsListDiffer = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(list: List<ProductItem>) = productsListDiffer.submitList(list)

    inner class ProductItemViewHolder(private val binding: ProductCardRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productItem: ProductItem) = with(binding) {
            productItemHeader.text = productItem.title
            productItemDescription.text = productItem.description
            productOrderButton.apply {
                text = productItem.price
                setOnClickListener { listener.onOrderBtnClick(productItem) }
            }
            Glide.with(productItemImage.context)
                .load(productItem.imageUrl)
                .placeholder(R.drawable.food_test)
                .into(productItemImage)

            itemView.setOnClickListener { listener.onItemClick(productItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductCardRecyclerItemBinding.inflate(inflater, parent, false)
        return ProductItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        val product = productsListDiffer.currentList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = productsListDiffer.currentList.size

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductItem>() {
            override fun areItemsTheSame(
                oldItem: ProductItem,
                newItem: ProductItem
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ProductItem,
                newItem: ProductItem
            ): Boolean =
                oldItem == newItem
        }
    }
}