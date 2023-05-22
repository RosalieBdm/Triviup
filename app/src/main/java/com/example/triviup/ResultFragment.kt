package com.example.triviup

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
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

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        databaseReference = Firebase.database.reference
        _binding = FragmentResultBinding.inflate(inflater)
        binding.textView.text = "Score : ${QuestionsFragment.score}"
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
            sendScore(binding.edittext.text.toString(), QuestionsFragment.score)
            findNavController().navigate(R.id.action_ResultFragment_to_RankingFragment)
        }
        binding.skipButton.setOnClickListener {
            findNavController().navigate(R.id.action_ResultFragment_to_RankingFragment)
        }

        binding.edittext.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                true
            } else {
                false
            }
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.konfettiView.start(party)
    }

    private fun sendScore(name: String, score: Int) {
        val rank = Rank(name, score)
        val id = databaseReference.push().key!!
        databaseReference.child("Ranking").child(id).setValue(rank)
    }


}