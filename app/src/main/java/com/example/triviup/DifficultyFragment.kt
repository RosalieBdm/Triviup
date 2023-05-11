package com.example.triviup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.triviup.databinding.FragmentDifficultyBinding
import com.example.triviup.model.Category

class DifficultyFragment : Fragment() {
    private lateinit var category: Category
    private var _binding: FragmentDifficultyBinding? = null;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDifficultyBinding.inflate(inflater)

        category = DifficultyFragmentArgs.fromBundle(requireArguments()).category

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.easyButton.setOnClickListener {
            this.findNavController().navigate(
            DifficultyFragmentDirections.actionDifficultyFragmentToQuestionsFragment(category, java.lang.String("easy"))
            )}
        binding.mediumButton.setOnClickListener {
            this.findNavController().navigate(
            DifficultyFragmentDirections.actionDifficultyFragmentToQuestionsFragment(category, java.lang.String("medium"))
            )}
        binding.hardButton.setOnClickListener {
            this.findNavController().navigate(
            DifficultyFragmentDirections.actionDifficultyFragmentToQuestionsFragment(category, java.lang.String("hard"))
            ) }
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}

