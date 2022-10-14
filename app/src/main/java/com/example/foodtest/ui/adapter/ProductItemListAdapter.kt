package com.example.foodtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodtest.R
import com.example.foodtest.databinding.ProductCardRecyclerItemBinding
import com.example.foodtest.ui.model.ProductItemPlug

class ProductItemListAdapter :
    RecyclerView.Adapter<ProductItemListAdapter.ProductItemViewHolder>() {

    private val productsListDiffer = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(list: List<ProductItemPlug>) = productsListDiffer.submitList(list)

    inner class ProductItemViewHolder(private val binding: ProductCardRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productItem: ProductItemPlug) = with(binding) {
            productItemHeader.text = productItem.title
            productItemDescription.text = productItem.description
            productOrderButton.text = productItem.price
            Glide.with(productItemImage.context)
                .load(productItem.imageUrl)
                .placeholder(R.drawable.food_test)
                .into(productItemImage)
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
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductItemPlug>() {
            override fun areItemsTheSame(
                oldItem: ProductItemPlug,
                newItem: ProductItemPlug
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ProductItemPlug,
                newItem: ProductItemPlug
            ): Boolean =
                oldItem == newItem
        }
    }
}