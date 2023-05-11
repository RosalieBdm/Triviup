package com.example.triviup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.triviup.adapter.CategoryAdapter
import com.example.triviup.adapter.CategoryClickListener
import com.example.triviup.database.Categories
import com.example.triviup.databinding.FragmentCategoryBinding
import com.example.triviup.databinding.FragmentMenuBinding
import com.example.triviup.viewmodel.CategoryViewModel
import com.example.triviup.viewmodel.CategoryViewModelFactory


class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.categoryButton.setOnClickListener {
            findNavController().navigate(R.id.action_MenuFragment_to_CategoryFragment)
        }
        binding.scoreboardButton.setOnClickListener {
            findNavController().navigate(R.id.action_MenuFragment_to_RankingFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

}