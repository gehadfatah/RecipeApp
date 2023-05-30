package com.goda.recipesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.goda.recipesapp.data.Model.Meal
import com.goda.recipesapp.databinding.ItemSaveBinding


class SaveAdapter : RecyclerView.Adapter<SaveAdapter.ViewHolder>() {
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    private val differCallback = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=  ViewHolder(
        // Alternatively inflate like usual, if you don't need binding
        ItemSaveBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .root
    )

    override fun onBindViewHolder(viewHolder: SaveAdapter.ViewHolder, position: Int)
    {
        ItemSaveBinding.bind(viewHolder.itemView).apply{
            val save = differ.currentList[position]
            Glide.with(viewHolder.itemView.context).load(save.strMealThumb).into(imageViewSavedMeal)
            textViewSavedMealTitle.text = save.strMeal
            this.root.setOnClickListener {
                onItemClickListener?.let { it(save) }
            }
        }
    }



    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Meal) -> Unit)? = null
    fun setOnItemClickListener(listener: (Meal) -> Unit) {
        onItemClickListener = listener
    }
}