package com.example.triviup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.triviup.adapter.RankListAdapter
import com.example.triviup.databinding.FragmentRankingBinding
import com.example.triviup.viewmodel.RankListViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ltu.m7019e.v23.themoviedb.viewmodel.RankListViewModelFactory


class RankingFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference

    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RankListViewModel
    private lateinit var viewModelFactory: RankListViewModelFactory

    private lateinit var rankListAdapter: RankListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databaseReference = Firebase.database.reference
        _binding = FragmentRankingBinding.inflate(inflater)

        // init viewModel for ranks
        val application = requireNotNull(this.activity).application
        viewModelFactory = RankListViewModelFactory(databaseReference, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RankListViewModel::class.java)

        // init adapter
        rankListAdapter = RankListAdapter()

        binding.recyclerView.adapter = rankListAdapter

        // observer for ranks
        viewModel.rankList.observe(viewLifecycleOwner) { rankList ->
            rankList?.let {
                rankListAdapter.submitList(rankList)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonToResult.setOnClickListener {
            //findNavController().navigate(R.id.action_RankingFragment_to_ResultFragment)
        }

    }

}
