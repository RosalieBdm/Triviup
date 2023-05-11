package com.example.triviup

import FactViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.triviup.databinding.FragmentFactBinding
import com.ltu.m7019e.v23.themoviedb.viewmodel.FactViewModelFactory

class FactFragment : Fragment() {
    private var fact : String =""

    private var _binding: FragmentFactBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FactViewModel
    private lateinit var viewModelFactory: FactViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFactBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        viewModelFactory = FactViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FactViewModel::class.java)

        viewModel.fact.observe(viewLifecycleOwner){ fact ->
            binding.fact = fact
        }

        binding.buttonNewFact.setOnClickListener {
            viewModel.getFact()
        }

        binding.menuButton.setOnClickListener {
            findNavController().navigate(R.id.action_factFragment_to_MenuFragment)
        }

        return binding.root
    }

}