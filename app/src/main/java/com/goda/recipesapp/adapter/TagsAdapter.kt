package com.goda.recipesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goda.recipesapp.databinding.ItemTagBinding


class TagsAdapter : RecyclerView.Adapter<TagsAdapter.ViewHolder>() {
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=  ViewHolder(
        // Alternatively inflate like usual, if you don't need binding
        ItemTagBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .root
    )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)
    {
        ItemTagBinding.bind(viewHolder.itemView).apply{
            val food = differ.currentList[position]
         tagNameTv.text=food
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }



}

typealias OnSelectClick = (String) -> Unit