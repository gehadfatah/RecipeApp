package com.goda.recipesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.goda.recipesapp.R
import com.goda.recipesapp.data.Model.Meal
import com.goda.recipesapp.databinding.ItemFoodBinding
import com.goda.recipesapp.databinding.ItemTagBinding

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    lateinit var  tagsAdapter:TagsAdapter
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
        ItemFoodBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .root
    )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)
    {
        ItemFoodBinding.bind(viewHolder.itemView).apply{
            val food = differ.currentList[position]
            Glide.with(viewHolder.itemView.context).load(food.strMealThumb).into(imageViewItemFood)
            nameFood.text = food.strMeal
            nameCategory.text = food.strCategory
            nameOrigin.text = food.strArea
            food.strTags?.split(",")?.let { setupRecyclerViewC(tagsRecycle, it) }
            viewHolder.itemView.setOnClickListener {
                onItemClickListener?.let { it(food) }
            }
        }
    }


    private fun setupRecyclerViewC(tagsRecycle: RecyclerView, tags: List<String>) {
        tagsAdapter = TagsAdapter()
        tagsRecycle.apply {
            adapter = tagsAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
        tagsAdapter.differ.submitList(tags)
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Meal) -> Unit)? = null
    private var onItemClickListener2: ((Meal) -> Unit)? = null
    fun setOnItemClickListener(listener: (Meal) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnItemClickListener2(listener: (Meal) -> Unit) {
        onItemClickListener2 = listener
    }

}