package com.goda.recipesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goda.recipesapp.features.home.data.models.Category
import com.goda.recipesapp.databinding.ItemCategoryBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    private val differCallback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=  ViewHolder(
        // Alternatively inflate like usual, if you don't need binding
        ItemCategoryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .root
    )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)
    {
        ItemCategoryBinding.bind(viewHolder.itemView).apply{
            val food = differ.currentList[position]
            Glide.with(viewHolder.itemView.context).load(food.strCategoryThumb).into(imageViewItemCategory)
            textViewItemCategoryNameCategory.text = food.strCategory
            viewHolder.itemView.setOnClickListener {
                onItemClickListener?.let { it(food) }
            }

        }
    }





    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Category) -> Unit)? = null
    fun setOnItemClickListener(listener: (Category) -> Unit) {
        onItemClickListener = listener
    }
}