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
import com.example.triviup.network.DataFetchStatus
import com.example.triviup.viewmodel.FactViewModelFactory

class FactFragment : Fragment() {

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
    ): View {

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

        viewModel.dataFetchStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (status) {
                    DataFetchStatus.LOADING -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.loading_animation)
                    }
                    DataFetchStatus.ERROR -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                    }
                    DataFetchStatus.DONE -> {
                        binding.statusImage.visibility = View.GONE
                    }
                }
            }
        }

        return binding.root
    }

}