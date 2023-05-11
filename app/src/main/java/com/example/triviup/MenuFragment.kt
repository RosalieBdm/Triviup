package com.example.triviup

import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.caverock.androidsvg.SVG
import com.example.triviup.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater)
        val svg = SVG.getFromResource(resources, R.raw.triviup_image)
        val drawable = PictureDrawable(svg.renderToPicture())
        binding.titleImage.setImageDrawable(drawable)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.categoryButton.setOnClickListener {
            findNavController().navigate(R.id.action_MenuFragment_to_CategoryFragment)
        }
        binding.scoreboardButton.setOnClickListener {
            findNavController().navigate(R.id.action_MenuFragment_to_RankingFragment)
        }

        binding.factButton.setOnClickListener {
            findNavController().navigate(R.id.action_MenuFragment_to_factFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

}