package com.goda.recipesapp.features.home.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.goda.recipesapp.R
import com.goda.recipesapp.adapter.CategoryAdapter
import com.goda.recipesapp.adapter.FoodAdapter
import com.goda.recipesapp.features.home.data.models.Category
import com.goda.recipesapp.data.Model.Meal
import com.goda.recipesapp.databinding.FragmentHomeBinding
import com.goda.recipesapp.features.home.ui.viewmodel.HomeViewModel
import com.goda.recipesapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var _binding: FragmentHomeBinding
    val viewModel by viewModels<HomeViewModel> ()
    lateinit var foodAdapter: FoodAdapter
    lateinit var categoryAdapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupRecyclerViewF()
        setupRecyclerViewC()
        initListener()
        initViewModel()
  }

    override fun onResume() {
        super.onResume()
    }
    private fun showLoading(state: Boolean) {
        _binding.swLayout.isRefreshing = state
    }

    private fun startshimmer() {
        _binding.shimmerRecommendation.startShimmer()
        _binding.shimmerCategories.startShimmer()
    }

    private fun removeShimmerMeals() {
        Handler().postDelayed({
            _binding.shimmerRecommendation.stopShimmer()
            _binding.shimmerRecommendation.visibility = View.INVISIBLE
        }, 500)
    }
    private fun removeShimmerCategories() {
        Handler().postDelayed({
            _binding.shimmerCategories.stopShimmer()
            _binding.shimmerCategories.visibility = View.GONE
        }, 500)
    }
    private fun initListener() {
        _binding.swLayout.post {

        }

        _binding.swLayout.setOnRefreshListener {
            showLoading(true)
            startshimmer()
            viewModel. getCategory()
        }

        foodAdapter.setOnItemClickListener2 {
            viewModel.saveFood(it)
            Snackbar.make(this.requireView(), "Recipe Saved", Snackbar.LENGTH_SHORT).apply {
                animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
                setBackgroundTint(Color.DKGRAY)
                setTextColor(Color.WHITE)
                show()
            }
        }


        foodAdapter.setOnItemClickListener { Meal ->
            val bundle = Bundle().apply {
                putSerializable("details", Meal)
                selectedMeal=Meal
                putBoolean("fromSave",false)
            }
            findNavController().navigate(R.id.action_home2_to_details, bundle)
        }
        categoryAdapter.setOnItemClickListener { category ->
            categoryy = category
            _binding.titleRE.text = category.strCategory+getString(R.string.title_meal)

            foodAdapter.differ.submitList(null)
            viewModel.getFilter(category.strCategory)
        }
    }

    private fun initViewModel() {
        startshimmer()
        viewModel.filter.observe(viewLifecycleOwner, Observer { response ->
            showLoading(false)
            removeShimmerCategories()
            when (response) {
                is Resource.Success -> {
                    response.data?.let { Random ->
                        foodAdapter.differ.submitList(Random.meals)
                        viewModel.getAllMealsCategoryDetail(Random.meals)
                    }
                }
                is Resource.Error -> {
                    response.message.let { msg ->
                        Toast.makeText(activity, "Error: $msg", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading->{ showLoading(true)

                }
                else -> {}
            }
        })
        viewModel.filterSecond.observe(viewLifecycleOwner, Observer { response ->
            //todo saveall items in database so not call again all these apis for meals again every time i load meals
            removeShimmerMeals()
            val newMeals= mutableListOf<Meal>()
            response?.forEach { item -> item.data?.meals?.let { newMeals.addAll(it) } }
            foodAdapter.differ.submitList(newMeals)
        })
        viewModel.categoryFood.observe(viewLifecycleOwner, Observer { response ->
            showLoading(false)
            when (response) {
                is Resource.Success -> {
                    response.data?.let { Category ->
                        categoryAdapter.differ.submitList(Category.categories)
                    }
                }
                is Resource.Error -> {
                    response.message.let { msg ->
                        Toast.makeText(activity, "Error: $msg", Toast.LENGTH_LONG).show()
                    }
                }
                else -> {}
            }
        })
    }

    private fun initView() {
        _binding.titleRE.text = if (categoryy!=null)(categoryy?.strCategory+getString(R.string.title_meal)) else getString(R.string.popular_recipes)

    }

    private fun setupRecyclerViewF() {
        foodAdapter = FoodAdapter()
        _binding.rvFood.apply {

            adapter = foodAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun setupRecyclerViewC() {
        categoryAdapter = CategoryAdapter()
        _binding.recyclerView.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    companion object {
        var categoryy: Category? = null
        var selectedMeal: Meal? = null
    }

}