package com.example.triviup

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.triviup.QuestionsFragment.Companion.correctAnswer
import com.example.triviup.QuestionsFragment.Companion.enableButtons
import com.example.triviup.QuestionsFragment.Companion.questionNumber
import com.example.triviup.QuestionsFragment.Companion.score
import com.example.triviup.adapter.QuestionAdapter
import com.example.triviup.adapter.QuestionClickListener

import com.example.triviup.databinding.FragmentResultsBinding

class ResultsFragment : Fragment() {


    private var _binding: FragmentResultsBinding? = null;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.scoreTextview.text = "$score"
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.backToCategories.setOnClickListener {
            findNavController().navigate(ResultsFragmentDirections.actionResultsFragmentToCategoryFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


}