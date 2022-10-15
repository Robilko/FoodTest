package com.example.foodtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodtest.R
import com.example.foodtest.databinding.BannerRecycleItemBinding
import com.example.foodtest.ui.adapter.listeners.BannerListListener
import com.example.foodtest.ui.model.BannerPlug

class BannerListAdapter(private val listener: BannerListListener) :
    RecyclerView.Adapter<BannerListAdapter.BannerViewHolder>() {

    private val bannersListDiffer = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(list: List<BannerPlug>) = bannersListDiffer.submitList(list)

    inner class BannerViewHolder(private val binding: BannerRecycleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bannerItem: BannerPlug) = with(binding) {
            Glide.with(bannerImage.context)
                .load(R.drawable.example_banner)
                .into(bannerImage)

            itemView.setOnClickListener { listener.onItemClick(bannerItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BannerRecycleItemBinding.inflate(inflater, parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val banner = bannersListDiffer.currentList[position]
        holder.bind(banner)
    }

    override fun getItemCount(): Int = bannersListDiffer.currentList.size

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BannerPlug>() {
            override fun areItemsTheSame(oldItem: BannerPlug, newItem: BannerPlug): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BannerPlug, newItem: BannerPlug): Boolean =
                oldItem == newItem
        }
    }
}