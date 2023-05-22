package com.example.triviup


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.GridLayoutManager
import com.example.triviup.adapter.CategoryAdapter
import com.example.triviup.adapter.CategoryClickListener
import com.example.triviup.database.Categories
import com.example.triviup.databinding.FragmentCategoryBinding

import com.example.triviup.viewmodel.CategoryViewModel
import com.example.triviup.viewmodel.CategoryViewModelFactory

class CategoryFragment : Fragment() {

    private lateinit var viewModel: CategoryViewModel
    private lateinit var viewModelFactory: CategoryViewModelFactory

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        viewModelFactory = CategoryViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CategoryViewModel::class.java)

        val gridLayoutManager = GridLayoutManager(this.context, 1)
        val recyclerView = binding.categoryRv
        recyclerView.layoutManager = gridLayoutManager

        val categoryAdapter = CategoryAdapter(
            CategoryClickListener { category ->
                viewModel.onCategoryItemClicked(category)
            })
        recyclerView.adapter = categoryAdapter

        val categoryList = Categories().list
        categoryAdapter.submitList(categoryList)

        viewModel.navigateToQuestions.observe(viewLifecycleOwner) { category ->
            category?.let {
                this.findNavController().navigate(
                    CategoryFragmentDirections.actionCategoryFragmentToDifficultyFragment(category)
                )
                viewModel.onQuestionsNavigated()
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

}