package com.example.triviup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.triviup.databinding.FragmentResultBinding
import com.example.triviup.model.Rank
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit


class ResultFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var party : Party

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databaseReference = Firebase.database.reference
        _binding = FragmentResultBinding.inflate(inflater)

        party = Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            spread = 360,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
            position = Position.Relative(0.5, 0.15)
        )

        binding.submitButton.setOnClickListener {
            binding.konfettiView.start(party)
        }

        return binding.root
    }


    fun sendScore(name: String, score: Int) {
        val rank = Rank(name, score)
        val id = databaseReference.push().key!!
        databaseReference.child("Ranking").child(id).setValue(rank)
    }


}