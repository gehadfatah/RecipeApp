package com.goda.recipesapp.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.goda.recipesapp.R
import com.goda.recipesapp.adapter.SaveAdapter
import com.goda.recipesapp.databinding.FragmentSaveFoodBinding
import com.goda.recipesapp.features.home.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveFoodFragment : Fragment(R.layout.fragment_save_food) {
    private lateinit var _binding: FragmentSaveFoodBinding
    val viewModel by viewModels<HomeViewModel> ()
    lateinit var saveFood: SaveAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSaveFoodBinding.inflate(inflater, container, false)
        return _binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        callbacksSettings()

    }

    private fun callbacksSettings() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val food = saveFood.differ.currentList[position]
                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    viewModel.deleteFood(food)
                    Snackbar.make(requireView(), "Recipe Deleted", Snackbar.LENGTH_LONG).apply {
                        animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
                        setBackgroundTint(Color.DKGRAY)
                        setTextColor(Color.WHITE)
                        show()
                    }.setAction("undo") {
                        viewModel.saveFood(food)
                    }
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(_binding.saveRv)
        }

        saveFood.setOnItemClickListener { Meal ->
            val bundle = Bundle().apply {
                putSerializable("details", Meal)
                putBoolean("fromSave",true)
            }
            findNavController().navigate(R.id.action_saveFood_to_details, bundle)
        }
    }

    private fun observeViewModel() {

        viewModel.getAllFood().observe(viewLifecycleOwner, Observer { it ->
            saveFood.differ.submitList(it)
        })
    }

    private fun setupRecyclerView() {
        saveFood = SaveAdapter()
        _binding.saveRv.apply {
            adapter = saveFood
            layoutManager = LinearLayoutManager(context)
        }
    }
}