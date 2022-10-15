package com.example.foodtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtest.databinding.CategoryButtonRecyclerItemBinding
import com.example.foodtest.ui.adapter.listeners.CategoryBtnListListener
import com.example.foodtest.utils.ui.Category

class CategoryButtonListAdapter(private val listener: CategoryBtnListListener) :
    RecyclerView.Adapter<CategoryButtonListAdapter.CategoryBtnViewHolder>() {

    lateinit var buttons: List<String>
    fun setCategoryBtnTitleList(titlesList: List<String>) {
        buttons = titlesList
    }

    inner class CategoryBtnViewHolder(private val binding: CategoryButtonRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int)  {
            binding.categoryBtn.apply {
                val btnTitle = buttons[position]
                text = btnTitle
                val category = Category.values()[position]
                setOnClickListener { listener.onClick(category) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryBtnViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryButtonRecyclerItemBinding.inflate(inflater, parent, false)
        return CategoryBtnViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryBtnViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = buttons.size
}