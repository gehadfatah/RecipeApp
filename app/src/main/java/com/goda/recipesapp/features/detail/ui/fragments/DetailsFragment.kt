package com.goda.recipesapp.features.detail.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goda.elmenus.util.extension.toggleVisibility
import com.goda.recipesapp.R
import com.goda.recipesapp.adapter.TagsAdapter
import com.goda.recipesapp.data.Model.Meal
import com.goda.recipesapp.data.Model.RandomMeals
import com.goda.recipesapp.databinding.FragmentDetailsBinding
import com.goda.recipesapp.features.detail.ui.viewmodel.DetailViewModel
import com.goda.recipesapp.util.Resource
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var _binding: FragmentDetailsBinding
    lateinit var tagsAdapter: TagsAdapter

    val viewModel by viewModels<DetailViewModel>()
    lateinit var food: Meal
    val args: DetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        _binding.apply {
            initListener(this, food)

        }
    }

    private fun initViewModel() {
        _binding.apply {
            viewModel.details.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { meal ->
                            setViewSettings(meal)
                        }
                    }
                    is Resource.Error -> {
                        response.message.let { msg ->
                            Toast.makeText(activity, "Error: $msg", Toast.LENGTH_LONG).show()
                        }
                    }

                    //  is Resource.Loading -> showLoading(response.loading == true)

                    else -> {

                    }
                }
            })
        }
    }

    private fun setViewSettings(meal: RandomMeals) {
        _binding.apply {
            cardViewSaveDish.toggleVisibility(!args.fromSave)
            for (item in meal.meals) {
                Glide.with(this.imageDishBig.context).load(item.strMealThumb)
                    .into(imageDishBig)
                nameFoodD.text = item.strMeal
                nameOrigin.text = item.strArea
                foodDesc.text =
                    item.strTags ?: "ingredient1: ${item.strMeasure1 + " " + item.strIngredient1}"
                // item.strTags?.takeIf { !it.isNullOrEmpty() } + " -> ingredient1: ${item.strMeasure1 + " " + item.strIngredient1}"
                nameCategory.text = item.strCategory
                textViewInstructionContent.text = ""
                val instructionsList = item.strInstructions?.split(".")
                //todo here some instruction not return as expect so i add number for instrument manual
                textViewInstructionContent.text = item.strInstructions
                if (instructionsList?.isNotEmpty() == true && !instructionsList[0].contains("1.")) {
                    //textViewInstructionContent.text = ""
                    instructionsList.forEachIndexed { index, element ->
                        if (index != instructionsList.size - 1) {
                            // textViewInstructionContent.append("${index.plus(1)}. $element \n")
                        }
                    }
                }
                showMoreCons.setOnClickListener {
                    if (showMoreCons.isVisible) {
                        textViewInstructionContent.maxLines = 50;
                        showMoreCons.toggleVisibility(false)
                    } else {
                        textViewInstructionContent.maxLines = 3;
                        showMoreCons.toggleVisibility(true)
                    }
                }
                food.strTags?.split(",")?.let { setupRecyclerViewC(tagsRecycle, it) }
                // go to youtube url
                play.setOnClickListener {
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(item.strYoutube))
                    startActivity(intent)
                }
            }
        }
    }

    private fun setupRecyclerViewC(tagsRecycle: RecyclerView, tags: List<String>) {
        tagsAdapter = TagsAdapter()
        tagsRecycle.apply {
            adapter = tagsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        tagsAdapter.differ.submitList(tags.filter{tag->tag.isNotEmpty()})
    }

    private fun initListener(fragmentDetailsBinding: FragmentDetailsBinding, food: Meal) {
        fragmentDetailsBinding.cardViewBack.setOnClickListener {
            findNavController().navigate(R.id.action_details_to_home2)
        }

        fragmentDetailsBinding.cardViewSaveDish.setOnClickListener {
            viewModel.saveFood(food)
            Snackbar.make(this.requireView(), "Recipe Saved", Snackbar.LENGTH_SHORT).apply {
                animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
                setBackgroundTint(Color.DKGRAY)
                setTextColor(Color.WHITE)
                show()
            }
        }
    }

    private fun initView() {
        food = args.details
        setToolbarSettings()
        if (food.strArea.isNullOrEmpty())
            viewModel.getDetails(food.idMeal)
        else
            setViewSettings(RandomMeals(listOf(food)))
    }

    private fun setToolbarSettings() {
        _binding.apply {
            toolbar.title = food.strMeal
            toolbar.setNavigationOnClickListener {
                // back button pressed
                // findNavController().navigate(R.id.action_details_to_home2)
                findNavController().popBackStack()
            }
        }
    }
}